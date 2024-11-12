package com.ank.japi.json;

import com.ank.japi.util.ArraySupport;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public record JsonArray(String name, boolean nullable)
        implements JsonField {

    public JsonArray(String name) {
        this( name, true );
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public JsonType getJsonType() {
        return JsonType.ARRAY;
    }

    @Override
    public boolean isAssignable(Object value) {
        return value == null
               ? nullable
               : value instanceof Iterable || ArraySupport.isArray( value );
    }

    @Override
    public boolean isNullable() {
        return nullable;
    }

    @Override
    public Value value() {
        return new Value() {

            private Iterable<?> value;

            @Override
            public Iterable<?> get() throws NoSuchElementException {
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
                if ( !isAssignable( value ) ) {
                    throw new NotAssignableException();
                }
                Iterable<?> val;
                if ( value instanceof Iterable<?> i ) {
                    val = i;
                }
                else {
                    int len = ArraySupport.getLength( value );
                    val = new ArrayList<>( len ) {{
                        ArraySupport.foreach( value, this::add );
                    }};
                }
                this.value = val;
            }
        };
    }
}
