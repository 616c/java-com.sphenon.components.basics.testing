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
import com.sphenon.basics.validation.returncodes.*;

import com.sphenon.basics.testing.*;

public class ClassTest extends com.sphenon.basics.testing.classes.TestBase {
    static protected long notification_level;
    static public    long adjustNotificationLevel(long new_level) { long old_level = notification_level; notification_level = new_level; return old_level; }
    static public    long getNotificationLevel() { return notification_level; }
    static { notification_level = NotificationLocationContext.getLevel(RootContext.getInitialisationContext(), "com.sphenon.basics.testing.classes.ClassTest"); };

    public ClassTest (CallContext context) {
    }

    public String getId(CallContext context) {
        if (this.id == null) {
            this.id = "ClassTest";
        }
        return this.id;
    }

    public TestResult perform (CallContext call_context, TestRun test_run) {
        Context context = Context.create(call_context);
        CustomaryContext cc = CustomaryContext.create(context);

        if ((this.notification_level & Notifier.CHECKPOINT) != 0) { CustomaryContext.create(Context.create(context)).sendTrace(context, Notifier.CHECKPOINT, "Hallo, ich bin der Test und performe gerade"); }
        if ((this.notification_level & Notifier.CHECKPOINT) != 0) { CustomaryContext.create(Context.create(context)).sendTrace(context, Notifier.CHECKPOINT, "Hallo, hier kommen die Daten: '%(@meinedaten)'", "meinedaten", "Meine Daten sind dieser Text"); }

        return TestResult.OK;
    }
}
