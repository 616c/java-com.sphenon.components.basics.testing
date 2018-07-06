// instantiated with jti.pl from Pair

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
// please do not modify this file directly
package com.sphenon.basics.testing.tplinst;

import com.sphenon.basics.testing.*;

import com.sphenon.basics.context.*;
import com.sphenon.basics.exception.*;
import com.sphenon.ui.annotations.*;

import com.sphenon.basics.many.returncodes.*;

@UIDelegate("js:instance.getItem2(context)") public class Pair_String_Test_ {
    private String item1;
    private Test item2;

    public Pair_String_Test_ (CallContext context, String item1, Test item2) {
        this.item1 = item1;
        this.item2 = item2;
    }

    public String getItem1(CallContext context) { return item1; }
    
    public void setItem1(CallContext context, String item1) { this.item1 = item1; }

    public Test getItem2(CallContext context) { return item2; }
    
    public void setItem2(CallContext context, Test item2) { this.item2 = item2; }
}
