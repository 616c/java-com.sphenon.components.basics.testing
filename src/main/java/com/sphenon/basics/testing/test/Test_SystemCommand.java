package com.sphenon.basics.testing.test;

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
import com.sphenon.basics.configuration.*;
import com.sphenon.basics.message.*;
import com.sphenon.basics.exception.*;
import com.sphenon.basics.notification.*;
import com.sphenon.basics.customary.*;
import com.sphenon.basics.testing.*;
import com.sphenon.basics.monitoring.*;

import com.sphenon.basics.testing.*;
import com.sphenon.basics.testing.classes.*;
import com.sphenon.basics.testing.tplinst.*;

import java.io.InputStreamReader;

public class Test_SystemCommand extends com.sphenon.basics.testing.classes.TestBase {

    public Test_SystemCommand (CallContext context) {
    }

    public String getId (CallContext context) {
        if (this.id == null) {
            this.id = this.system_command;
        }
        return this.id;
    }

    protected String system_command;

    public String getSystemCommand (CallContext context) {
        return this.system_command;
    }

    public void setSystemCommand (CallContext context, String system_command) {
        this.system_command = system_command;
    }

    protected String working_folder;

    public String getWorkingFolder (CallContext context) {
        return this.working_folder;
    }

    public void setWorkingFolder (CallContext context, String working_folder) {
        this.working_folder = working_folder;
    }

    public TestResult perform (CallContext call_context, TestRun test_run) {
        Context context = Context.create(call_context);

        try {

            InputStreamReader process_stdout = null;
            InputStreamReader process_stderr = null;
            int               exit_value;

            java.lang.Process process = java.lang.Runtime.getRuntime().exec(this.system_command, null, new java.io.File(this.working_folder));

            if (process != null) {
                process_stdout = new InputStreamReader(process.getInputStream());
                process_stderr = new InputStreamReader(process.getErrorStream());
                String out = "";
                String err = "";
                char [] buf = new char[1];
                do {
                    Thread.currentThread().sleep(500);
                    while (process_stdout.ready()) {
                        process_stdout.read(buf, 0, 1);
                        out += buf[0];
                    }
                    while (process_stderr.ready()) {
                        process_stderr.read(buf, 0, 1);
                        err += buf[0];
                    }
                    try {
                        int ev = process.exitValue();
                        break;
                    } catch (IllegalThreadStateException itse) {
                        continue;
                    }
                } while (true);
            }

        } catch (Throwable t) {
            return new TestResult_ExceptionRaised(context, t);
        }
        
        return TestResult.OK;
    }
}
