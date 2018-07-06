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
import com.sphenon.basics.many.*;
import com.sphenon.basics.many.tplinst.*;

import com.sphenon.basics.testing.*;

public class TestMain extends com.sphenon.basics.testing.classes.TestBase {
    static protected long notification_level;
    static public    long adjustNotificationLevel(long new_level) { long old_level = notification_level; notification_level = new_level; return old_level; }
    static public    long getNotificationLevel() { return notification_level; }
    static { notification_level = NotificationLocationContext.getLevel(RootContext.getInitialisationContext(), "com.sphenon.basics.testing.classes.TestMain"); };

    public TestMain (CallContext context) {
    }

    public String getId(CallContext context) {
        if (this.id == null) {
            this.id = "Invocation:"+ class_name + ".main(";
            boolean first = true;
            for (String argument : arguments.getIterable_String_(context)) {
                if ( ! first) {
                    this.id += ",";
                } else {
                    first = false;
                }
                this.id += "\"" + argument + "\"";
            }
            this.id += ")";
        }
        return this.id;
    }

    protected String class_name;

    public String getClassName (CallContext context) {
        return this.class_name;
    }

    public void setClassName (CallContext context, String class_name) {
        this.class_name = class_name;
    }

    protected Vector_String_long_ arguments;

    public Vector_String_long_ getArguments (CallContext context) {
        return this.arguments;
    }

    public void setArguments (CallContext context, Vector_String_long_ arguments) {
        this.arguments = arguments;
    }

    public TestResult perform (CallContext context, TestRun test_run) {

        try {

            Class main_class = com.sphenon.basics.cache.ClassCache.getClassForName(context, this.class_name);
            java.lang.reflect.Method main = main_class.getMethod("main", String[].class);
            String[] args = new String[(int) this.arguments.getSize(context)];
            int a = 0;
            for (String arg : this.arguments.getIterable_String_(context)) {
                args[a++] = arg;
            }
            main.invoke(null, (Object) args);
           
        } catch (Throwable t) {
            return new TestResult_ExceptionRaised(context, t);
        }

        return TestResult.OK;
    }
}
