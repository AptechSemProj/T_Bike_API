package com.ank.japi.json;

import java.util.NoSuchElementException;

public record JsonString(String name, boolean nullable)
        implements JsonField {

    public JsonString(String name) {
        this( name, true );
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public JsonType getJsonType() {
        return JsonType.STRING;
    }

    @Override
    public boolean isAssignable(Object value) {
        return value == null
               ? nullable
               : value instanceof CharSequence;
    }

    @Override
    public boolean isNullable() {
        return nullable;
    }

    @Override
    public Value value() {
        return new Value() {

            private String value;

            @Override
            public Object get() throws NoSuchElementException {
                if ( value != null ) {
                    return value;
                }
                if ( nullable ) {
                    return null;
                }
                throw new NoSuchElementException();
            }

            @Override
            public void set(Object value) throws NotAssignableException {
                if ( isAssignable( value ) ) {
                    this.value = value == null
                                 ? null
                                 : value.toString();
                }
                else {
                    throw new NotAssignableException();
                }
            }
        };
    }
}
