package com.ank.japi.json;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

public abstract class JsonObject
        implements JsonField {

    public static final  String NAME_SEPARATOR   = ".";
    private static final int    HASH_MAP_LIMITED = 10;

    private final String                 name;
    private final boolean                nullable;
    private       boolean                modifiable = true;
    private       Map<String, JsonField> structure;

    public JsonObject(String name, boolean nullable) {
        this.structure = new HashMap<>();
        this.nullable = nullable;
        this.name = name;
    }

    public JsonObject(String name) {
        this( name, true );
    }

    protected void initStructure() {
    }

    private void recreateStructure() {
        if ( !modifiable ) {
            return;
        }
        if ( structure instanceof LinkedHashMap ) {
            return;
        }
        if ( structure.size() > HASH_MAP_LIMITED ) {
            structure = new LinkedHashMap<>( structure );
        }
    }

    protected final void addField(JsonField field) {
        if ( !modifiable ) {
            return;
        }
        structure.put( field.getName(), field );
        recreateStructure();
    }

    public final void setModifiable(boolean flag) {
        modifiable = flag;
        if ( modifiable ) {
            if ( structure.size() < HASH_MAP_LIMITED ) {
                structure = new HashMap<>( structure );
            }
            else {
                structure = new LinkedHashMap<>( structure );
            }
        }
        else {
            this.structure = Map.copyOf( structure );
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public JsonType getJsonType() {
        return JsonType.OBJECT;
    }

    @Override
    public boolean isAssignable(Object value) {
        return value == null
               ? nullable
               : value instanceof Map;
    }

    @Override
    public boolean isNullable() {
        return nullable;
    }

    @Override
    public ObjectValue value() {
        return new ObjectValue( this );
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( !(o instanceof JsonObject that) ) {
            return false;
        }
        return nullable == that.nullable
                && modifiable == that.modifiable
                && Objects.equals( name, that.name )
                && Objects.equals( structure, that.structure );
    }

    @Override
    public int hashCode() {
        return Objects.hash( name, nullable, modifiable, structure );
    }

    public static final class ObjectValue
            implements Value {

        private final JsonObject          field;
        private       Map<String, Object> value;

        private ObjectValue(JsonObject field) {
            this.field = field;
        }

        @Override
        public Map<String, Object> get() throws NoSuchElementException {
            if ( value != null ) {
                return value;
            }
            if ( field.nullable ) {
                return null;
            }
            throw new NoSuchElementException();
        }

        private void set(JsonField f, Object o) {
            if ( this.value == null ) {
                this.value = new HashMap<>();
            }
            Value fv = f.value();
            fv.set( o );
            this.value.put( f.getName(), fv.get() );
        }

        public void set(String name, Object value) {
            if ( name == null ) {
                throw new IllegalArgumentException( "name is null" );
            }
            JsonField f = this.field.structure.get( name );
            if ( f != null ) {
                set( f, value );
            }
            else {
                throw new NullPointerException( name + " not found" );
            }
        }

        @Override
        public void set(Object value) throws NotAssignableException {
            if ( !this.field.isAssignable( value ) ) {
                throw new NotAssignableException();
            }
            @SuppressWarnings("unchecked")
            Map<String, Object> fields = (Map<String, Object>) value;
            this.value = fields.size() > HASH_MAP_LIMITED
                         ? new LinkedHashMap<>()
                         : new HashMap<>();
            this.field.structure.forEach(
                    (n, f) -> set( f, fields.get( n ) )
            );
        }
    }
}
