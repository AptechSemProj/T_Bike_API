package com.ank.japi.json;

import com.ank.japi.exception.NotAssignableException;

import java.util.NoSuchElementException;

public class JsonString
        implements Json {

    private final boolean nullable;
    private       String  value;

    public JsonString(boolean nullable) {
        this.nullable = nullable;
    }

    @Override
    public boolean isAssignable(Object o) {
        if ( o == null ) {
            return nullable;
        }
        return o instanceof CharSequence;
    }

    @Override
    public Object get() throws NoSuchElementException {
        if ( isAssignable( value ) ) {
            return value;
        }
        throw new NoSuchElementException();
    }

    @Override
    public void set(Object o) throws NotAssignableException {
        if ( !isAssignable( o ) ) {
            throw new NotAssignableException();
        }
        if ( o == null ) {
            this.value = null;
        }
        else {
            this.value = o.toString();
        }
    }
}
