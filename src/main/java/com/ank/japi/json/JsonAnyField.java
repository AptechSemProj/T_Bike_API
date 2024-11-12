package com.ank.japi.json;

import com.ank.japi.exception.NotAssignableException;

import java.util.NoSuchElementException;
import java.util.Objects;

public final class JsonAnyField
        implements JsonField {

    private final String  name;
    private final boolean nullable;

    public JsonAnyField(String name, boolean nullable) {
        this.name = Objects.requireNonNull( name );
        this.nullable = nullable;
    }

    public JsonAnyField(String name) {
        this( name, true );
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isNullable() {
        return nullable;
    }

    @Override
    public Value value() {
        return new Value() {

            private Object value;

            @Override
            public boolean isAssignable(Object o) {
                return o != null || nullable;
            }

            @Override
            public Object get()
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
                this.value = o;
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if ( o == this ) {
            return true;
        }
        if ( !(o instanceof JsonAnyField that) ) {
            return false;
        }
        return Objects.equals( this.name, that.name )
                && this.nullable == that.nullable;
    }

    @Override
    public int hashCode() {
        return Objects.hash( name, nullable );
    }
}
