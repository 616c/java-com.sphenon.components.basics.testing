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
@UIClassifier("Vector_Test_")
@UIParts("js:instance.getIterable(context)")
public interface Vector_Test_long_
  extends ReadOnlyVector_Test_long_,
          WriteVector_Test_long_
          , GenericVector<Test>
          , GenericIterable<Test>
{
    public Test                                    get             (CallContext context, long index) throws DoesNotExist;
    public Test                                    tryGet          (CallContext context, long index);
    public boolean                                     canGet          (CallContext context, long index);

    public ReferenceToMember_Test_long_ReadOnlyVector_Test_long__  getReference    (CallContext context, long index) throws DoesNotExist;
    public ReferenceToMember_Test_long_ReadOnlyVector_Test_long__  tryGetReference (CallContext context, long index);

    public Test                                    set             (CallContext context, long index, Test item);
    public void                                        add             (CallContext context, long index, Test item) throws AlreadyExists;
    public void                                        prepend         (CallContext context, Test item);
    public void                                        append          (CallContext context, Test item);
    public void                                        insertBefore    (CallContext context, long index, Test item) throws DoesNotExist;
    public void                                        insertBehind    (CallContext context, long index, Test item) throws DoesNotExist;
    public Test                                    replace         (CallContext context, long index, Test item) throws DoesNotExist;
    public Test                                    unset           (CallContext context, long index);
    public Test                                    remove          (CallContext context, long index) throws DoesNotExist;

    public IteratorItemIndex_Test_long_       getNavigator    (CallContext context);

    public long                                        getSize         (CallContext context);

    // for sake of Iterable's
    public java.util.Iterator<Test>              getIterator_Test_ (CallContext context);
    public java.util.Iterator                          getIterator (CallContext context);
    public VectorIterable_Test_long_          getIterable_Test_ (CallContext context);
    public Iterable<Test> getIterable (CallContext context);
}
