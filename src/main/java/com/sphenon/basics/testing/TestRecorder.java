package com.sphenon.basics.testing;

/****************************************************************************
  Copyright 2001-2018 Sphenon GmbH

  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
  License for the specific language governing permissions and limitations
  under the License.
*****************************************************************************/

import com.sphenon.basics.context.*;
import com.sphenon.basics.context.classes.*;
import com.sphenon.basics.configuration.*;
import com.sphenon.basics.message.*;
import com.sphenon.basics.exception.*;
import com.sphenon.basics.notification.*;
import com.sphenon.basics.notification.classes.*;
import com.sphenon.basics.customary.*;
import com.sphenon.basics.expression.*;
import com.sphenon.basics.validation.returncodes.*;

import java.io.*;
import java.util.regex.*;

public class TestRecorder {
    static protected Configuration config;
    static { config = Configuration.create(RootContext.getInitialisationContext(), "com.sphenon.basics.testing.TestRecorder"); };

    static protected long notification_level;
    static public    long adjustNotificationLevel(long new_level) { long old_level = notification_level; notification_level = new_level; return old_level; }
    static public    long getNotificationLevel() { return notification_level; }
    static { notification_level = NotificationLocationContext.getLevel(RootContext.getInitialisationContext(), "com.sphenon.basics.testing.TestRecorder"); };

    protected String id;
    protected Context parents_context;

    protected String folder;
    protected String reference_folder;
    protected ChannelPrintStream notification_log;
    protected ChannelPrintStream test_log;
    protected java.io.PrintStream notification_log_stream;
    protected java.io.PrintStream test_log_stream;
    protected String diff_command;
    protected String notification_log_filename;
    protected String test_log_filename;
    protected String test_ref_filename;

    protected long mode;
    final static protected long IGNORE_MODE           = 0;
    final static protected long RECORD_MODE           = 1;
    final static protected long VALIDATE_MODE         = 2;
    final static protected long RECORD_REFERENCE_MODE = 3;

    protected TestRecorder (Context context, String id) { // Achtung! Parameter ist kein CallContext
        this.parents_context = context;
        this.id = id;
    }

    static public TestRecorder create(Context context, String id) { // Achtung! Parameter ist kein CallContext
        return new TestRecorder(context, id);
    }

    static protected RegularExpression rep1  = new RegularExpression("^(?:ctn|oorl)://", "");
    static protected RegularExpression rep2  = new RegularExpression("/", ".");
    static protected RegularExpression rep3  = new RegularExpression("[^A-Za-z0-9_\\.]", "");
    static protected RegularExpression attre = new RegularExpression("\\[@AID:([0-9]+)_([A-Za-z0-9_-]+)@\\]", "[@AID:_$2@]");
    static protected final String      attfilepre  = "attachment-";
    static protected final String      attfilepost = ".dat";

    protected class TestRecorderAttechmentHandler implements AttachmentHandler {
        protected String base_folder;

        public TestRecorderAttechmentHandler(CallContext context, String base_folder) {
            this.base_folder = base_folder;
        }

        public void handleAttachment(CallContext context, String attachment_id, String data) {
            String attachment_filename = this.base_folder + "/attachment-" + attachment_id + ".dat";
            java.io.FileOutputStream attachment;
            try {
                attachment = new java.io.FileOutputStream(attachment_filename);
            } catch (java.io.FileNotFoundException fnfe) {
                CustomaryContext.create(Context.create(context)).throwEnvironmentError(context, fnfe, "Could not open '%(file)'", "file", attachment_filename);
                throw (ExceptionEnvironmentError) null; // compiler insists
            }
            try {
                attachment.write(data.getBytes());
                attachment.close();
            } catch (java.io.IOException ioe) {
                CustomaryContext.create(Context.create(context)).throwEnvironmentError(context, ioe, "Could not write to or close '%(file)'", "file", attachment_filename);
                throw (ExceptionEnvironmentError) null; // compiler insists
            }
        }
    }

    public void start (CallContext context, String test_run_id) {
        CustomaryContext cc = CustomaryContext.create((Context) context);

        this.mode = getMode(context, this.id);

        if (this.mode >= RECORD_MODE) {
            if (test_run_id == null) { test_run_id = (new java.text.SimpleDateFormat ("yyyyMMddHHmmss")).format(new java.util.Date()); }

            String[] logprop = getProperty4Class(context, "TestLogFolder4Class", this.id);
            this.folder = (logprop == null ? null : logprop[0]);

            if (this.folder == null && this.mode >= RECORD_MODE) {
                cc.throwConfigurationError(context, "Cannot collect or validate checkpoints, no folder configured (property '.TestLogFolder4Class')");
                throw (ExceptionConfigurationError) null; // compiler insists
            }

            this.folder += "/" + test_run_id + "/" + rep3.replaceAll(context, rep2.replaceAll(context, rep1.replaceAll(context, this.id)));
            new java.io.File(this.folder).mkdirs();

            notification_log_filename = this.folder + "/notification.log";
            test_log_filename = this.folder + "/test.log";

            if (this.mode >= VALIDATE_MODE) {
                String[] refprop = getProperty4Class(context, "TestReferenceFolder4Class", this.id);
                this.reference_folder = (refprop == null ? null : refprop[0]);

                if (this.reference_folder == null) {
                    cc.throwConfigurationError(context, "Cannot validate checkpoints or record reference, no folder configured (property '.TestReferenceFolder4Class')");
                    throw (ExceptionConfigurationError) null; // compiler insists
                }

                this.reference_folder += "/" + rep3.replaceAll(context, rep2.replaceAll(context, rep1.replaceAll(context, this.id)));
                
                if (this.mode >= RECORD_REFERENCE_MODE) {
                    new java.io.File(this.reference_folder).mkdirs();
                }

                test_ref_filename = this.reference_folder + "/test.log";
            }

            try {
                this.notification_log = ChannelPrintStream.createChannelPrintStream(parents_context, this.notification_log_stream = new java.io.PrintStream(new java.io.FileOutputStream(notification_log_filename)));
            } catch (java.io.FileNotFoundException fnfe) {
                cc.throwEnvironmentError(context, fnfe, "Could not open '%(file)'", "file", notification_log_filename);
                throw (ExceptionEnvironmentError) null; // compiler insists
            }
            String tlog = (this.mode == RECORD_REFERENCE_MODE ? test_ref_filename : test_log_filename);
            try {
                this.test_log = ChannelPrintStream.createChannelPrintStream(parents_context, this.test_log_stream = new java.io.PrintStream(new java.io.FileOutputStream(tlog)));
            } catch (java.io.FileNotFoundException fnfe) {
                cc.throwEnvironmentError(context, fnfe, "Could not open '%(file)'", "file", tlog);
                throw (ExceptionEnvironmentError) null; // compiler insists
            }
            this.test_log.setAttachmentHandler(context, new TestRecorderAttechmentHandler(parents_context, this.mode == RECORD_REFERENCE_MODE ? this.reference_folder : this.folder));

            NotificationContext nc = NotificationContext.create(parents_context);
            NotifierClass cpdr_logger = NotifierClass.createNotifierClass(parents_context);
            cpdr_logger.setParent(parents_context, nc.getNotifier(parents_context));
            cpdr_logger.setTransientChannel(parents_context, this.notification_log);
            cpdr_logger.setTestLogChannel(parents_context, this.test_log);
            nc.setNotifier(parents_context, cpdr_logger);
        } 
   }

    public void stop (CallContext call_context) {
        if (this.mode >= RECORD_MODE) {
            this.notification_log_stream.close();
            this.test_log_stream.close();
        }
    }

    public void validate (CallContext context) throws ValidationFailure {
        CustomaryContext cc = CustomaryContext.create((Context) context);

        if (this.mode == VALIDATE_MODE) {

            String test_dif_filename = this.folder + "/test.log.diff";

            if ( ! (new File(test_log_filename)).exists()) {
                ValidationFailure.createAndThrow(context, "Test '%(testid)' result log does not exist (test log: '%(log)')", "testid", this.id, "log", test_log_filename);
            }

            if ( ! (new File(test_ref_filename)).exists()) {
                ValidationFailure.createAndThrow(context, "Test '%(testid)' result reference log does not exist (test reference log: '%(reflog)')", "testid", this.id, "reflog", test_ref_filename);
            }

            String test_log_filename_canonical = test_log_filename + ".cmp.tmp";
            String test_ref_filename_canonical = test_ref_filename + ".cmp.tmp";
            copyWithAIDRemoval(context, test_log_filename, test_log_filename_canonical);
            copyWithAIDRemoval(context, test_ref_filename, test_ref_filename_canonical);

            if ( ! areFilesEqual(context, test_log_filename_canonical, test_ref_filename_canonical, test_dif_filename)) {
                ValidationFailure.createAndThrow(context, "Test '%(testid)' result log differs from reference log (result log: '%(log)', reference log: '%(ref)')", "testid", this.id, "log", test_log_filename_canonical, "ref", test_ref_filename_canonical);
            }

            new File(test_log_filename_canonical).delete();
            new File(test_ref_filename_canonical).delete();

            try {
                FileReader frlog = new FileReader(test_log_filename);
                FileReader frref = new FileReader(test_ref_filename);
                BufferedReader brlog = new BufferedReader(frlog);
                BufferedReader brref = new BufferedReader(frref);
                
                int linenbr = 0;
                String logline;
                String refline;
                boolean logmf;
                boolean refmf;
                while ((logline = brlog.readLine()) != null && (refline = brref.readLine()) != null) {
                    linenbr++;
                    Matcher logm = attre.getMatcher(context, logline);
                    Matcher refm = attre.getMatcher(context, refline);
                    while ((logmf = logm.find()) && (refmf = refm.find())) {
                        String anbrl = logm.group(1);
                        String anbrr = refm.group(1);
                        String namel = logm.group(2);
                        String namer = refm.group(2);
                        if ( ! namel.equals(namer)) {
                            ValidationFailure.createAndThrow(context, "Test '%(testid)' result log differs from reference log, line '%(linenbr)': attachment names '%(name1)' and '%(name2)' differ", "testid", this.id, "linenbr", t.s(linenbr), "name1", namel, "name2", namer);
                        }
                        String log_attachment = this.folder + "/" + attfilepre + anbrl + "_" + namel + attfilepost;
                        String ref_attachment = this.reference_folder + "/" + attfilepre + anbrr + "_" + namer + attfilepost;
                        String dif_attachment = this.folder + "/" + attfilepre + anbrl + "_" + namel + attfilepost + ".diff";

                        if ( ! (new File(log_attachment)).exists()) {
                            ValidationFailure.createAndThrow(context, "Test '%(testid)' result attachment '%(name)' does not exist, line '%(linenbr)' of test log  (test log: '%(log)', attachment: '%(logatt)')", "testid", this.id, "linenbr", t.s(linenbr), "name", namel, "log", test_log_filename, "logatt", log_attachment);
                        }

                        if ( ! (new File(ref_attachment)).exists()) {
                            ValidationFailure.createAndThrow(context, "Test '%(testid)' result reference attachment '%(name)' does not exist, line '%(linenbr)' of test log  (test log: '%(log)', reference attachment: '%(refatt)')", "testid", this.id, "linenbr", t.s(linenbr), "name", namel, "log", test_log_filename, "refatt", ref_attachment);
                        }
                        
                        if ( ! areFilesEqual(context, log_attachment, ref_attachment, dif_attachment)) {
                            ValidationFailure.createAndThrow(context, "Test '%(testid)' result attachment '%(name)' differs from reference attachment, line '%(linenbr)' of test log  (test log: '%(log)', attachment: '%(logatt)', reference attachment: '%(refatt)')", "testid", this.id, "linenbr", t.s(linenbr), "name", namel, "log", test_log_filename, "logatt", log_attachment, "refatt", ref_attachment);
                        }
                    }
                }
                brlog.close();
                brref.close();
                frlog.close();
                frref.close();
            } catch (IOException ioe) {
                cc.throwEnvironmentError(context, ioe, "Comparison failed unexpectedly");
                throw (ExceptionEnvironmentError) null; // compiler insists
            }
        }
    }

    protected void copyWithAIDRemoval(CallContext context, String orig, String copy) {
        CustomaryContext cc = CustomaryContext.create((Context) context);

        try {
            FileReader fr = new FileReader(orig);
            BufferedReader br = new BufferedReader(fr);
            FileWriter fw = new FileWriter(copy);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
                
            String line;
            while ((line = br.readLine()) != null) {
                pw.println(attre.replaceAll(context, line));
            }
            pw.close();
            bw.close();
            fw.close();
            br.close();
            fr.close();
        } catch (IOException ioe) {
            cc.throwEnvironmentError(context, ioe, "Copy failed unexpectedly");
            throw (ExceptionEnvironmentError) null; // compiler insists
        }
    }

    protected boolean areFilesEqual(CallContext context, String file1, String file2, String out) {
        CustomaryContext cc = CustomaryContext.create((Context) context);

        if (diff_command == null) {
            diff_command = config.get(context, "TestDiffCommand", "/usr/bin/diff %%file1%% %%file2%% > %%out%%");
        }

        String   dcmd  = diff_command.replaceAll("%%file1%%", file1).replaceAll("%%file2%%", file2).replaceAll("%%out%%", out);
        String[] dcmda = new String[3];
        dcmda[0] = "/bin/bash";
        dcmda[1] = "-c";
        dcmda[2] = dcmd;

        if ((this.notification_level & Notifier.SELF_DIAGNOSTICS) != 0) { cc.sendTrace(context, Notifier.SELF_DIAGNOSTICS, "TestRecorder, comparing files: '%(cmd)'", "cmd", dcmd); }

        java.lang.Process process = null;
        try {
            process = java.lang.Runtime.getRuntime().exec(dcmda, null, null);
            process.waitFor();
        } catch (java.io.IOException ioe) {
            cc.throwEnvironmentError(context, ioe, "Could not execute diff command '%(cmd)'", "cmd", dcmd);
            throw (ExceptionEnvironmentError) null; // compiler insists
        } catch (java.lang.InterruptedException ie) {
            cc.throwEnvironmentError(context, ie, "Could not execute diff command '%(cmd)'", "cmd", dcmd);
            throw (ExceptionEnvironmentError) null; // compiler insists
        }
		
        boolean are_equal = (process.exitValue() == 0 ? true : false);

        if ((this.notification_level & Notifier.SELF_DIAGNOSTICS) != 0) { cc.sendTrace(context, Notifier.SELF_DIAGNOSTICS, "TestRecorder, files are equal? '%(result)' (diff exit value '%(exit)')", "result", t.s(are_equal), "exit", t.s(process.exitValue())); }

        if (are_equal) {
            new File(out).delete();
        }

        return are_equal;
    }

    protected void copyFile(CallContext context, String source, String destination) {
        final int BUFFER_SIZE = 32768;

        try {
            InputStream in = new FileInputStream(source);
            try {
                OutputStream out = new FileOutputStream(destination);
                try {
                    byte[] buffer = new byte[BUFFER_SIZE];
                    int len;
                    while ((len = in.read(buffer)) > 0) {
                        out.write(buffer, 0, len);
                    }
                } finally {
                    out.close();
                }
            } finally {
                in.close();
            }
        } catch (IOException ioe) {
            CustomaryContext.create((Context)context).throwEnvironmentError(context, ioe, "Could not copy file '%(source)' to '%(destination)'", "source", source, "destination", destination);
            throw (ExceptionEnvironmentError) null; // compiler insists
        }
    }

    //----------------------------------------------------------------------------------------------

    static protected String property_prefix = "com.sphenon.basics.testing.TestRecorder";

    static protected String[] getProperty4Class (CallContext context, String property_name, String class_path) {
        String[] result = new String[2];
        int idx = class_path.lastIndexOf(';');
        if (idx != -1) {
            class_path = class_path.substring(0, idx);
        }
        while (class_path != null) {
            String search_for = property_prefix + "." + property_name + "." + class_path;
            result[1] = search_for;
            String property = Configuration.get(context, search_for, (String) null, (String) null);
            if (property != null) {
                result[0] = property;
                return result;
            }
            idx = class_path.lastIndexOf('/');
            class_path = (idx == -1 ? null : class_path.substring(0, idx));
        }
        return null;
    }

    static protected long getMode (CallContext context, String class_path) {
        String[] result = getProperty4Class(context, "Mode4Class", class_path);
        return (result == null ? IGNORE_MODE : parseProperty(context, result[0], result[1]));
    }


    static protected long parseProperty (CallContext call_context, String property, String full_property_name) {
        Context context = Context.create(call_context);
        CustomaryContext cc = CustomaryContext.create(context);
        if (property.equals("IGNORE"))                    { return IGNORE_MODE; }
        if (property.equals("RECORD"))                    { return RECORD_MODE; }
        if (property.equals("VALIDATE"))                  { return VALIDATE_MODE; }
        if (property.equals("RECORD_REFERENCE"))     { return RECORD_REFERENCE_MODE; }
        cc.sendCaution(context, "Property '%(full_property_name)' contains invalid entry '%(entry)'", "full_property_name", full_property_name, "entry", property);
        return IGNORE_MODE;
    }
}
