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
import com.sphenon.basics.context.classes.*;
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

public class Test_Repeater extends com.sphenon.basics.testing.classes.TestBase {
    static protected long notification_level;
    static public    long adjustNotificationLevel(long new_level) { long old_level = notification_level; notification_level = new_level; return old_level; }
    static public    long getNotificationLevel() { return notification_level; }
    static { notification_level = NotificationLocationContext.getLevel(RootContext.getInitialisationContext(), "com.sphenon.basics.testing.test.Test_Repeater"); };

    public Test_Repeater (CallContext context) {
    }

    public String getId (CallContext context) {
        if (this.id == null) {
            this.id = this.test.getId(context) + "[" + this.repetitions + "x]";
        }
        return this.id;
    }

    protected com.sphenon.basics.testing.Test test;

    public com.sphenon.basics.testing.Test getTest (CallContext context) {
        return this.test;
    }

    public void setTest (CallContext context, com.sphenon.basics.testing.Test test) {
        this.test = test;
    }

    protected int repetitions;

    public int getRepetitions (CallContext context) {
        return this.repetitions;
    }

    public void setRepetitions (CallContext context, int repetitions) {
        this.repetitions = repetitions;
    }

    public TestResult perform (CallContext call_context, TestRun test_run) {
        Context context = Context.create(call_context);

        try {

            long start = System.currentTimeMillis();

            for (int i=0; i<this.repetitions; i++) {
                this.test.perform(context, test_run);
            }

            long stop = System.currentTimeMillis();

            if ((this.notification_level & Notifier.DIAGNOSTICS) != 0) { CustomaryContext.create(context).sendTrace(context, Notifier.DIAGNOSTICS, "Execution of test took %(duration) msec", "duration", t.s(stop-start));  }

        } catch (Throwable t) {
            return new TestResult_ExceptionRaised(context, t);
        }
        
        return TestResult.OK;
    }
}
