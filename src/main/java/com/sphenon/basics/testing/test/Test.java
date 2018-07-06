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

import com.sphenon.basics.testing.*;
import com.sphenon.basics.testing.classes.*;
import com.sphenon.basics.testing.tplinst.*;

public class Test {

    public static com.sphenon.basics.testing.Test getTest (CallContext call_context) {
        Context context = Context.create(call_context);
        CustomaryContext cc = CustomaryContext.create(context);

        TestPackage tp11 = new ClassTestPackage(context);
        tp11.getTests(context).append(context, new Pair_String_Test_(context, "tp111", new ClassTest(context)));
        tp11.getTests(context).append(context, new Pair_String_Test_(context, "tp112", new ClassTest(context)));
        tp11.getTests(context).append(context, new Pair_String_Test_(context, "tp113", new ClassTest(context)));
        TestPackage tp12 = new ClassTestPackage(context);
        tp12.getTests(context).append(context, new Pair_String_Test_(context, "tp121", new ClassTest(context)));
        tp12.getTests(context).append(context, new Pair_String_Test_(context, "tp122", new ClassTest(context)));
        tp12.getTests(context).append(context, new Pair_String_Test_(context, "tp123", new ClassTest(context)));
        TestPackage tp13 = new ClassTestPackage(context);
        tp13.getTests(context).append(context, new Pair_String_Test_(context, "tp131", new ClassTest(context)));
        tp13.getTests(context).append(context, new Pair_String_Test_(context, "tp132", new ClassTest(context)));
        tp13.getTests(context).append(context, new Pair_String_Test_(context, "tp133", new ClassTest(context)));
        TestPackage tp21 = new ClassTestPackage(context);
        tp21.getTests(context).append(context, new Pair_String_Test_(context, "tp211", new ClassTest(context)));
        tp21.getTests(context).append(context, new Pair_String_Test_(context, "tp212", new ClassTest(context)));
        tp21.getTests(context).append(context, new Pair_String_Test_(context, "tp213", new ClassTest(context)));
        TestPackage tp22 = new ClassTestPackage(context);
        tp22.getTests(context).append(context, new Pair_String_Test_(context, "tp221", new ClassTest(context)));
        tp22.getTests(context).append(context, new Pair_String_Test_(context, "tp222", new ClassTest(context)));
        tp22.getTests(context).append(context, new Pair_String_Test_(context, "tp223", new ClassTest(context)));
        TestPackage tp23 = new ClassTestPackage(context);
        tp23.getTests(context).append(context, new Pair_String_Test_(context, "tp231", new ClassTest(context)));
        tp23.getTests(context).append(context, new Pair_String_Test_(context, "tp232", new ClassTest(context)));
        tp23.getTests(context).append(context, new Pair_String_Test_(context, "tp233", new ClassTest(context)));
        TestPackage tp31 = new ClassTestPackage(context);
        tp31.getTests(context).append(context, new Pair_String_Test_(context, "tp311", new ClassTest(context)));
        tp31.getTests(context).append(context, new Pair_String_Test_(context, "tp312", new ClassTest(context)));
        tp31.getTests(context).append(context, new Pair_String_Test_(context, "tp313", new ClassTest(context)));
        TestPackage tp32 = new ClassTestPackage(context);
        tp32.getTests(context).append(context, new Pair_String_Test_(context, "tp321", new ClassTest(context)));
        tp32.getTests(context).append(context, new Pair_String_Test_(context, "tp322", new ClassTest(context)));
        tp32.getTests(context).append(context, new Pair_String_Test_(context, "tp323", new ClassTest(context)));
        TestPackage tp33 = new ClassTestPackage(context);
        tp33.getTests(context).append(context, new Pair_String_Test_(context, "tp331", new ClassTest(context)));
        tp33.getTests(context).append(context, new Pair_String_Test_(context, "tp332", new ClassTest(context)));
        tp33.getTests(context).append(context, new Pair_String_Test_(context, "tp333", new ClassTest(context)));
        TestPackage tp1 = new ClassTestPackage(context);
        tp1.getTests(context).append(context, new Pair_String_Test_(context, "tp11", tp11));
        tp1.getTests(context).append(context, new Pair_String_Test_(context, "tp12", tp12));
        tp1.getTests(context).append(context, new Pair_String_Test_(context, "tp13", tp13));
        TestPackage tp2 = new ClassTestPackage(context);
        tp2.getTests(context).append(context, new Pair_String_Test_(context, "tp21", tp21));
        tp2.getTests(context).append(context, new Pair_String_Test_(context, "tp22", tp22));
        tp2.getTests(context).append(context, new Pair_String_Test_(context, "tp23", tp23));
        TestPackage tp3 = new ClassTestPackage(context);
        tp3.getTests(context).append(context, new Pair_String_Test_(context, "tp31", tp31));
        tp3.getTests(context).append(context, new Pair_String_Test_(context, "tp32", tp32));
        tp3.getTests(context).append(context, new Pair_String_Test_(context, "tp33", tp33));
        TestPackage tp = new ClassTestPackage(context);
        tp.getTests(context).append(context, new Pair_String_Test_(context, "tp1", tp1));
        tp.getTests(context).append(context, new Pair_String_Test_(context, "tp2", tp2));
        tp.getTests(context).append(context, new Pair_String_Test_(context, "tp3", tp3));

        return tp;
    }

    public static void main(String[] args) {
        Context context = com.sphenon.basics.context.classes.RootContext.getRootContext ();
        CustomaryContext cc = CustomaryContext.create(context);

        Configuration.initialise(context);

        cc.sendTrace(context, Notifier.CHECKPOINT, "Test com.sphenon.basics.testing begin");

        com.sphenon.basics.testing.Test t = getTest(context);

        TestRun test_run = new ClassTestRun(context);
        cc.sendTrace(context, Notifier.CHECKPOINT, "Test result: " + t.perform(context, test_run));

        cc.sendTrace(context, Notifier.CHECKPOINT, "Test com.sphenon.basics.testing end");
    }
}
