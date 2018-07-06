package com.sphenon.basics.testing.factories;

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
import com.sphenon.basics.testing.tplinst.*;
import com.sphenon.basics.testing.classes.*;

public class Factory_TestPackage {

    public Factory_TestPackage (CallContext context) {
    }

    protected String id;
    private int id_validation_state = -1;

    protected Vector_Pair_String_Test__long_ tests;
    private int tests_validation_state = -1;

    public String getId (CallContext context) {
        return this.id;
    }

    public String defaultId (CallContext context) {
        return null;
    }

    public void setId (CallContext context, String id) {
        this.id = id;
        this.id_validation_state = -1;
    }

    public void validateId(CallContext call_context) throws ValidationFailure {
        Context context = Context.create(call_context);
        CustomaryContext cc = CustomaryContext.create(context);
        if (this.id_validation_state == -1) {
            if (this.id == null && this.id_path == null) {
                this.id_validation_state = 1;
            } else {
                this.id_validation_state = 0;
            }
        }
        switch (this.id_validation_state) {
            case 0:
                return;
            case 1:
                ValidationFailure.createAndThrow(context, "Parameter not set: 'id'");
                throw (ValidationFailure) null; // compiler insists
        }
    }

    protected String id_path;

    public String getIdPath (CallContext context) {
        return this.id_path;
    }

    public void setIdPath (CallContext context, String id_path) {
        this.id_path = id_path;
        this.id_validation_state = -1;
    }

    public String defaultIdPath (CallContext context) {
        return null;
    }

    public Vector_Pair_String_Test__long_ getTests (CallContext context) {
        return this.tests;
    }

    public Vector_Pair_String_Test__long_ defaultTests (CallContext context) {
        return Factory_Vector_Pair_String_Test__long_.construct(context);
    }

    public void setTests (CallContext context, Vector_Pair_String_Test__long_ tests) {
        this.tests = tests;
        this.tests_validation_state = -1;
    }

    public void validateTests(CallContext call_context) throws ValidationFailure {
        Context context = Context.create(call_context);
        CustomaryContext cc = CustomaryContext.create(context);
        if (this.tests_validation_state == -1) {
            if (this.tests == null) {
                this.tests_validation_state = 1;
            } else {
                this.tests_validation_state = 0;
            }
        }
        switch (this.tests_validation_state) {
            case 0:
                return;
            case 1:
                ValidationFailure.createAndThrow(context, "Parameter not set: 'tests'");
                throw (ValidationFailure) null; // compiler insists
        }
    }

    public TestPackage createTestPackage (CallContext call_context) {
        Context context = Context.create(call_context);
        CustomaryContext cc = CustomaryContext.create(context);

        try {
            validateTests(context);
        } catch (ValidationFailure vf) {
            cc.throwPreConditionViolation(context, vf, "Parameter 'Tests' ('%(tests)') is invalid", "tests", this.tests);
        }

        try {
            validateId(context);
        } catch (ValidationFailure vf) {
            cc.throwPreConditionViolation(context, vf, "Parameter 'Id' ('%(id)') is invalid", "id", this.id);
        }

        return new ClassTestPackage(context, this.tests, this.id != null ? this.id : (this.id_path == null ? null : this.id_path.replaceFirst("^.*/","")));
    }
}
