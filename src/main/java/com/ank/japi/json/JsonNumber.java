package com.ank.japi.json;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.NoSuchElementException;
import java.util.Objects;

public final class JsonNumber
        implements JsonField {

    private final String  name;
    private final boolean nullable;
    private       boolean onlyInteger = false;
    private       boolean onlyFloat   = false;

    public JsonNumber(String name, boolean nullable) {
        this.name = name;
        this.nullable = nullable;
    }

    public JsonNumber(String name) {
        this( name, true );
    }

    public JsonNumber acceptAll() {
        onlyInteger = false;
        onlyFloat = false;
        return this;
    }

    public JsonNumber acceptOnlyInteger() {
        onlyInteger = true;
        onlyFloat = false;
        return this;
    }

    public JsonNumber acceptOnlyFloat() {
        onlyFloat = true;
        onlyInteger = false;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public JsonType getJsonType() {
        return JsonType.NUMBER;
    }

    @Override
    public boolean isAssignable(Object value) {
        if ( value == null ) {
            return nullable;
        }
        if ( onlyFloat ) {
            return value instanceof Float || value instanceof Double
                    || value instanceof BigDecimal;
        }
        if ( onlyInteger ) {
            return value instanceof Byte || value instanceof Short
                    || value instanceof Integer || value instanceof Long
                    || value instanceof BigInteger;
        }
        return value instanceof Number && value instanceof Comparable;
    }

    @Override
    public boolean isNullable() {
        return nullable;
    }

    @Override
    public Value value() {
        return new Value() {

            private Number value;

            @Override
            public Number get() throws NoSuchElementException {
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
                    this.value = (Number) value;
                }
                else {
                    throw new NotAssignableException();
                }
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( !(o instanceof JsonNumber that) ) {
            return false;
        }
        return nullable == that.nullable
                && onlyInteger == that.onlyInteger
                && onlyFloat == that.onlyFloat
                && Objects.equals( name, that.name );
    }

    @Override
    public int hashCode() {
        return Objects.hash( name, nullable, onlyInteger, onlyFloat );
    }
}
