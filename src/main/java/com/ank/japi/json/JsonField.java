package com.ank.japi.json;

import com.ank.japi.exception.NotAssignableException;

import java.util.NoSuchElementException;

public interface JsonField {

    interface Value {

        boolean isAssignable(Object o);

        Object get() throws NoSuchElementException;

        void set(Object o) throws NotAssignableException;

    }

    String getName();

    boolean isNullable();

    Value value();

    boolean equals(Object o);

    int hashCode();

}
