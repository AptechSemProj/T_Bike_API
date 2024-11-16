package com.ank.japi.json;

import com.ank.japi.exception.NotAssignableException;
import com.ank.japi.util.ArraySupport;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public final class JsonArray
        implements Json {

    private final boolean      nullable;
    private       List<Object> value;

    public JsonArray(boolean nullable) {
        this.nullable = nullable;
    }

    @Override
    public boolean isAssignable(Object o) {
        if ( o == null ) {
            return nullable;
        }
        return o instanceof Iterable
                || ArraySupport.isArray( o );
    }

    @Override
    public boolean isArray() {
        return true;
    }

    @Override
    public Iterable<Object> get()
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
        this.value = null;
        if ( o instanceof Iterable<?> i ) {
            i.forEach( this::add );
        }
        else {
            ArraySupport.forEach( o, this::add );
        }
    }

    public void add(Object value) {
        if ( this.value == null ) {
            this.value = new ArrayList<>();
        }
        this.value.add( value );
    }
}
