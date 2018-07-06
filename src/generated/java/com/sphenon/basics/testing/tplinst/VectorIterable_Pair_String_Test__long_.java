// instantiated with jti.pl from VectorIterable

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

import com.sphenon.basics.many.*;
import com.sphenon.basics.many.returncodes.*;

public class VectorIterable_Pair_String_Test__long_ implements Iterable<Pair_String_Test_>
{
    protected java.util.Iterator<Pair_String_Test_> iterator;

    public VectorIterable_Pair_String_Test__long_ (CallContext context, Vector_Pair_String_Test__long_ vector) {
        this.iterator = (vector == null ? (new java.util.Vector<Pair_String_Test_>()).iterator() : vector.getIterator_Pair_String_Test__(context));
    }

    public java.util.Iterator<Pair_String_Test_> iterator () {
        return this.iterator;
    }
}
