package com.ank.japi.json;

import java.util.NoSuchElementException;

public record JsonBoolean(String name, boolean nullable)
        implements JsonField {

    public JsonBoolean(String name) {
        this( name, true );
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public JsonType getJsonType() {
        return JsonType.BOOLEAN;
    }

    @Override
    public boolean isAssignable(Object value) {
        return value == null
               ? nullable
               : value instanceof Boolean;
    }

    @Override
    public boolean isNullable() {
        return nullable;
    }

    @Override
    public Value value() {
        return new Value() {

            private Boolean value;

            @Override
            public Boolean get() throws NoSuchElementException {
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
                    this.value = (Boolean) value;
                }
                else {
                    throw new NotAssignableException();
                }
            }
        };
    }
}
