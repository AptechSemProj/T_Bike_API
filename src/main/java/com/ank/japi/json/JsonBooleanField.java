package com.ank.japi.json;

import java.util.Objects;

public final class JsonBooleanField
        implements JsonField {

    private final String  name;
    private final boolean nullable;

    public JsonBooleanField(String name, boolean nullable) {
        this.name = Objects.requireNonNull( name );
        this.nullable = nullable;
    }

    public JsonBooleanField(String name) {
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
        return new JsonBoolean( nullable );
    }

    @Override
    public boolean equals(Object o) {
        if ( o == this ) {
            return true;
        }
        if ( !(o instanceof JsonBooleanField that) ) {
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
