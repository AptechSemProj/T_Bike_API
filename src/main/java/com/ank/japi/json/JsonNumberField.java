package com.ank.japi.json;

import java.util.Objects;

public final class JsonNumberField
        implements JsonField {

    private final String  name;
    private final boolean nullable;
    private       boolean onlyInteger = false;
    private       boolean onlyFloat   = false;

    public JsonNumberField(String name, boolean nullable) {
        this.name = Objects.requireNonNull( name );
        this.nullable = nullable;
    }

    public JsonNumberField(String name) {
        this( name, true );
    }

    public JsonNumberField acceptAll() {
        onlyInteger = false;
        onlyFloat = false;
        return this;
    }

    public JsonNumberField acceptOnlyInteger() {
        onlyInteger = true;
        onlyFloat = false;
        return this;
    }

    public JsonNumberField acceptOnlyFloat() {
        onlyFloat = true;
        onlyInteger = false;
        return this;
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
        return new JsonNumber( nullable, onlyInteger, onlyFloat );
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( !(o instanceof JsonNumberField that) ) {
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
