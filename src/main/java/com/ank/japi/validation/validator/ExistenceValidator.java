package com.ank.japi.validation.validator;

import com.ank.japi.validation.Validator;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;

@Deprecated
public final class ExistenceValidator<T>
        extends Validator {

    public static final int ALREADY_EXISTS = 0;
    public static final int NOT_EXIST      = 1;

    private Predicate<T> tester;
    private int          check = ALREADY_EXISTS;

    public ExistenceValidator(Collection<T> collection) {
        if ( collection == null ) {
            throw new IllegalArgumentException();
        }
        this.tester = collection::contains;
    }

    public ExistenceValidator() {
        this( Collections.emptySet() );
    }

    public ExistenceValidator<T> acceptAlreadyExists() {
        check = ALREADY_EXISTS;
        return this;
    }

    public ExistenceValidator<T> acceptNotExist() {
        check = NOT_EXIST;
        return this;
    }

    public ExistenceValidator<T> setTester(Predicate<T> func) {
        this.tester = func;
        return this;
    }

}
