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
import com.sphenon.basics.message.*;
import com.sphenon.basics.notification.*;
import com.sphenon.basics.notification.classes.*;
import com.sphenon.basics.customary.*;
import com.sphenon.basics.monitoring.*;
import com.sphenon.basics.validation.returncodes.*;
import com.sphenon.ui.annotations.*;

import com.sphenon.basics.testing.*;
import com.sphenon.basics.testing.classes.*;
import com.sphenon.basics.testing.tplinst.*;

@UIParts( { "js:instance.getTests(context) == null ? null : instance.getTests(context).getIterable(context)" } )
public class RecordedTest extends com.sphenon.basics.testing.classes.TestBase {
    static protected long notification_level;
    static public    long adjustNotificationLevel(long new_level) { long old_level = notification_level; notification_level = new_level; return old_level; }
    static public    long getNotificationLevel() { return notification_level; }
    static { notification_level = NotificationLocationContext.getLevel(RootContext.getInitialisationContext(), "com.sphenon.basics.testing.RecordedTest"); };

    protected Test test;

    public String getId(CallContext context) {
        if (this.id == null && this.test.getId(context) != null) {
            this.id = this.test.getId(context) + "[recorded]";
        }
        return this.id;
    }

    public void setId (CallContext context, String id) {
        if (this.test instanceof TestBase) {
            ((TestBase) this.test).setId(context, id);
        } else {
            super.setId(context, id);
        }
    }

    public String getPath(CallContext context) {
        if (this.path == null && this.test.getPath(context) != null) {
            this.path = this.test.getPath(context) + "[recorded]";
        }
        return this.path;
    }

    public void setPath (CallContext context, String path) {
        if (this.test instanceof TestBase) {
            ((TestBase) this.test).setPath(context, path);
        } else {
            super.setPath(context, path);
        }
    }

    public Vector_Pair_String_Test__long_ getTests(CallContext context) {
        if (this.test instanceof TestPackage) {
            return ((TestPackage) this.test).getTests(context);
        } else {
            return null;
        }
    }

    public RecordedTest (CallContext context, Test test) {
        this.test = test;
    }

    public TestResult perform (CallContext call_context, TestRun test_run) {
        Context context = Context.create(call_context);
        CustomaryContext cc = CustomaryContext.create(context);

        TestRecorder trec = TestRecorder.create(context, test.getPath(context));

        trec.start(context, test_run.getId(context));

        TestResult tr;

        try {

            tr = test.perform(context, test_run);

        } finally {
            trec.stop(context);
        }

        if (tr.getProblemState(context).isRed(context)) {
            if ((this.notification_level & Notifier.PRODUCTION) != 0) { cc.sendError(context, "Test failed: %(state), %(problem)", "state", tr.getProblemState(context), "problem", tr.getProblem(context)); }
            return tr;
        }

        try {
            trec.validate(context);
        } catch (ValidationFailure vf) {
            if ((this.notification_level & Notifier.PRODUCTION) != 0) { cc.sendError(context, "Checkpoint data validation failed: %(reason)", "reason", vf); }
            return new TestResult_ExceptionRaised(context, vf);
        }

        return tr;
    }
}
