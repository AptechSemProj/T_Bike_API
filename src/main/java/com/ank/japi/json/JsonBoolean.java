package com.ank.japi.json;

import com.ank.japi.exception.NotAssignableException;

import java.util.NoSuchElementException;

public final class JsonBoolean
        implements Json {

    private final boolean nullable;
    private       Boolean value;

    public JsonBoolean(boolean nullable) {
        this.nullable = nullable;
    }

    @Override
    public boolean isAssignable(Object o) {
        if ( o == null ) {
            return nullable;
        }
        return o instanceof Boolean;
    }

    @Override
    public Boolean get()
    throws NoSuchElementException {
        if ( isAssignable( value ) ) {
            return value;
        }
        throw new NoSuchElementException();
    }

    @Override
    public void set(Object o)
    throws NotAssignableException {
        if ( !isAssignable( o ) ) {
            throw new NotAssignableException();
        }
        this.value = (Boolean) o;
    }
}
