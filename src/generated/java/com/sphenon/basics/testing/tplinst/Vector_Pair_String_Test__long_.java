// instantiated with jti.pl from Vector

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

import com.sphenon.ui.annotations.*;

@UIId("")
@UIName("")
@UIClassifier("Vector_Pair_String_Test__")
@UIParts("js:instance.getIterable(context)")
public interface Vector_Pair_String_Test__long_
  extends ReadOnlyVector_Pair_String_Test__long_,
          WriteVector_Pair_String_Test__long_
          , GenericVector<Pair_String_Test_>
          , GenericIterable<Pair_String_Test_>
{
    public Pair_String_Test_                                    get             (CallContext context, long index) throws DoesNotExist;
    public Pair_String_Test_                                    tryGet          (CallContext context, long index);
    public boolean                                     canGet          (CallContext context, long index);

    public ReferenceToMember_Pair_String_Test__long_ReadOnlyVector_Pair_String_Test__long__  getReference    (CallContext context, long index) throws DoesNotExist;
    public ReferenceToMember_Pair_String_Test__long_ReadOnlyVector_Pair_String_Test__long__  tryGetReference (CallContext context, long index);

    public Pair_String_Test_                                    set             (CallContext context, long index, Pair_String_Test_ item);
    public void                                        add             (CallContext context, long index, Pair_String_Test_ item) throws AlreadyExists;
    public void                                        prepend         (CallContext context, Pair_String_Test_ item);
    public void                                        append          (CallContext context, Pair_String_Test_ item);
    public void                                        insertBefore    (CallContext context, long index, Pair_String_Test_ item) throws DoesNotExist;
    public void                                        insertBehind    (CallContext context, long index, Pair_String_Test_ item) throws DoesNotExist;
    public Pair_String_Test_                                    replace         (CallContext context, long index, Pair_String_Test_ item) throws DoesNotExist;
    public Pair_String_Test_                                    unset           (CallContext context, long index);
    public Pair_String_Test_                                    remove          (CallContext context, long index) throws DoesNotExist;

    public IteratorItemIndex_Pair_String_Test__long_       getNavigator    (CallContext context);

    public long                                        getSize         (CallContext context);

    // for sake of Iterable's
    public java.util.Iterator<Pair_String_Test_>              getIterator_Pair_String_Test__ (CallContext context);
    public java.util.Iterator                          getIterator (CallContext context);
    public VectorIterable_Pair_String_Test__long_          getIterable_Pair_String_Test__ (CallContext context);
    public Iterable<Pair_String_Test_> getIterable (CallContext context);
}
