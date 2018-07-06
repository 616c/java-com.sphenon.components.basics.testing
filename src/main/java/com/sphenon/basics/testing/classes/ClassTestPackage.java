package com.sphenon.basics.testing.classes;

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
import com.sphenon.basics.customary.*;
import com.sphenon.basics.monitoring.*;

import com.sphenon.basics.testing.*;
import com.sphenon.basics.testing.classes.*;
import com.sphenon.basics.testing.tplinst.*;

public class ClassTestPackage extends TestBase implements TestPackage {
    static protected long notification_level;
    static public    long adjustNotificationLevel(long new_level) { long old_level = notification_level; notification_level = new_level; return old_level; }
    static public    long getNotificationLevel() { return notification_level; }
    static { notification_level = NotificationLocationContext.getLevel(RootContext.getInitialisationContext(), "com.sphenon.basics.testing.classes.ClassTestPackage"); };

    protected Vector_Pair_String_Test__long_ tests = null;

    public ClassTestPackage (CallContext context, Vector_Pair_String_Test__long_ tests, String id) {
        this.tests = tests;
        this.id = id;
    }

    public ClassTestPackage (CallContext context) {
    }

    public void setPath (CallContext context, String path) {
        this.path = path;
        for (Iterator_Pair_String_Test__ ipst = this.tests.getNavigator(context);
             ipst.canGetCurrent(context);
             ipst.next(context)
            ) {
            Pair_String_Test_ pst = ipst.tryGetCurrent(context);
            Test t = pst.getItem2(context);
            if (t.getPath(context) == null && t instanceof TestBase) {
                ((TestBase) t).setPath(context, this.getPath(context) + "/" + ((TestBase) t).getId(context));
            }
        }
    }

    public Vector_Pair_String_Test__long_ getTests(CallContext context) {
        if (this.tests == null) {
            this.tests = Factory_Vector_Pair_String_Test__long_.construct(context);
        }
        return this.tests;
    }

    public TestResult perform (CallContext context, TestRun test_run) {

        for (Iterator_Pair_String_Test__ ipst = this.tests.getNavigator(context);
             ipst.canGetCurrent(context);
             ipst.next(context)
            ) {
            Pair_String_Test_ pst = ipst.tryGetCurrent(context);
            Test t = pst.getItem2(context);
            if (test_run == null || test_run.matchesTest(context, t) || t instanceof TestPackage) {
                TestResult tr = t.perform(context, test_run);
                if (tr.getProblemState(context) != ProblemState.OK) {
                    return tr;
                }
            } else {
                // skipped in this run
            }
        }

        return TestResult.OK;
    }
}
