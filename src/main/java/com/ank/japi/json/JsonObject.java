package com.ank.japi.json;

import com.ank.japi.exception.NotAssignableException;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class JsonObject
        implements Json {

    private final Map<String, JsonField> structure;
    private final boolean                nullable;
    private       Map<String, Object>    value;

    public JsonObject(Map<String, JsonField> structure, boolean nullable) {
        this.structure = structure;
        this.nullable = nullable;
    }

    @Override
    public boolean isAssignable(Object o) {
        if ( o == null ) {
            return nullable;
        }
        return o instanceof Map;
    }

    @Override
    public boolean isObject() {
        return true;
    }

    @Override
    public Map<String, Object> get()
    throws NoSuchElementException {
        if ( isAssignable( value ) ) {
            return value;
        }
        throw new NoSuchElementException();
    }

    public Object get(String name) {
        if ( value == null ) {
            return null;
        }
        return value.get( name );
    }

    @Override
    public void set(Object o)
    throws NotAssignableException {
        if ( !isAssignable( o ) ) {
            throw new NotAssignableException();
        }
        Map<?, ?> fields = (Map<?, ?>) o;
        this.value = null;
        this.structure.forEach( (n, f) -> set( f, fields.get( n ) ) );
    }

    public void set(String name, Object value) {
        if ( name == null ) {
            throw new IllegalArgumentException( "name is null" );
        }
        JsonField f = this.structure.get( name );
        if ( f == null ) {
            throw new NullPointerException( "Field '" + name + "' not found" );
        }
        set( f, value );
    }

    private void set(JsonField f, Object o) {
        if ( this.value == null ) {
            this.value = new HashMap<>();
        }
        JsonField.Value fv = f.value();
        fv.set( o );
        this.value.put( f.getName(), fv.get() );
    }
}
