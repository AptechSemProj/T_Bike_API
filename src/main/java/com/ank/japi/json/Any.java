package com.ank.japi.json;

import java.util.NoSuchElementException;

public record Any(String name, boolean nullable)
        implements JsonField {

    public Any(String name) {
        this( name, true );
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public JsonType getJsonType() {
        return JsonType.ANY;
    }

    @Override
    public boolean isAssignable(Object value) {
        return value != null || nullable;
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
            public Object get() throws NoSuchElementException {
                if ( value != null ) {
                    return value;
                }
                else if ( nullable ) {
                    return null;
                }
                else {
                    throw new NoSuchElementException();
                }
            }

            @Override
            public void set(Object value) throws NotAssignableException {
                if ( isAssignable( value ) ) {
                    this.value = value;
                }
                else {
                    throw new NotAssignableException();
                }
            }
        };
    }
}
