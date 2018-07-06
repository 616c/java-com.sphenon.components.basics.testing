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

import com.sphenon.basics.testing.*;

import java.util.Vector;

public class TestWithAssertions extends com.sphenon.basics.testing.classes.TestBase {
    static protected long notification_level;
    static public    long adjustNotificationLevel(long new_level) { long old_level = notification_level; notification_level = new_level; return old_level; }
    static public    long getNotificationLevel() { return notification_level; }
    static { notification_level = NotificationLocationContext.getLevel(RootContext.getInitialisationContext(), "com.sphenon.basics.testing.TestWithAssertions"); };

    protected Test test;
    protected String test_id;

    public TestWithAssertions (CallContext context, Test test, String test_id) {
        this.test = test;
        this.test_id = test_id;
    }

    public String getId(CallContext context) {
        if (this.id == null) {
            this.id = this.test.getId(context) + "[assertions]";
        }
        return this.id;
    }

    protected Vector<Condition> pre_conditions;

    public Vector<Condition> getPreConditions (CallContext context) {
        return this.pre_conditions;
    }

    public void setPreConditions (CallContext context, Vector<Condition> pre_conditions) {
        this.pre_conditions = pre_conditions;
    }

    protected Vector<Condition> post_conditions;

    public Vector<Condition> getPostConditions (CallContext context) {
        return this.post_conditions;
    }

    public void setPostConditions (CallContext context, Vector<Condition> post_conditions) {
        this.post_conditions = post_conditions;
    }

    public TestResult perform (CallContext call_context, TestRun test_run) {
        Context context = Context.create(call_context);
        CustomaryContext cc = CustomaryContext.create(context);

        if (this.pre_conditions != null) {
            for (Condition pre_condition : this.pre_conditions) {
                if (pre_condition.isTrue(context) == false) {
                    if ((this.notification_level & Notifier.PRODUCTION) != 0) { cc.sendError(context, "Checkpoint pre condition validation failed, expression '%(condition)' is not true", "condition", pre_condition.toString(context)); }
                    return new TestResult_Failure(context, "PreCondition failed: " + pre_condition.toString(context));
                }
            }
        }

        TestResult tr = test.perform(context, test_run);

        if (tr.getProblemState(context).isRed(context)) {
            if ((this.notification_level & Notifier.PRODUCTION) != 0) { cc.sendError(context, "Test failed: %(state), %(problem)", "state", tr.getProblemState(context), "problem", tr.getProblem(context)); }
            return tr;
        }

        if (this.post_conditions != null) {
            for (Condition post_condition : this.post_conditions) {
                if (post_condition.isTrue(context) == false) {
                    if ((this.notification_level & Notifier.PRODUCTION) != 0) { cc.sendError(context, "Checkpoint post condition validation failed, expression '%(condition)' is not true", "condition", post_condition.toString(context)); }
                    return new TestResult_Failure(context, "PostCondition failed: " + post_condition.toString(context));
                }
            }
        }

        return tr;
    }
}
