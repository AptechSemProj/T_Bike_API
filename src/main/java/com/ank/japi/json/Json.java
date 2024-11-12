package com.ank.japi.json;

import java.io.Serializable;

public interface Json
        extends JsonField.Value,
                Serializable {

//    private final Map<String, JsonField> fields;
//    private       Object                 value;
//
//    public Json(final Map<String, JsonField> fields) {
//        this.fields = fields;
//        if ( fields != null ) {
//            this.value = new LinkedHashMap<String, Object>();
//        }
//    }

    default boolean isObject() {
        return false;
    }

    default boolean isArray() {
        return false;
    }

    default boolean isStructured() {
        return isObject() || isArray();
    }

//    {
//        if ( isObject() ) {
//            throw new UnsupportedOperationException(
//                    "Unknown field to set value because JSON is an object"
//            );
//        }
//        this.value = value;
//    }

//    @SuppressWarnings("unchecked")
//    public void add(Object value) {
//        if ( !isArray() ) {
//            throw new UnsupportedOperationException( "JSON is not an array" );
//        }
//        if ( this.value == null ) {
//            this.value = new LinkedList<>();
//        }
//        ((List<Object>) this.value).add( value );
//    }

//    @SuppressWarnings("unchecked")
//    public void set(String name, Object value) {
//        if ( !isObject() ) {
//            throw new UnsupportedOperationException( "JSON is not an object" );
//        }
//        JsonField jf = fields.get( name );
//        if ( jf == null ) {
//            throw new NullPointerException( "No such field: " + name );
//        }
//        JsonField.Value jv = jf.value();
//        jv.set( value );
//        if ( this.value == null ) {
//            this.value = new LinkedHashMap<String, Object>();
//        }
//        ((Map<String, Object>) this.value).put( jf.getName(), jv.get() );
//    }

//    @SuppressWarnings("unchecked")
//    public Object get(String name) {
//        if ( !isObject() ) {
//            throw new UnsupportedOperationException( "JSON is not an object" );
//        }
//        if ( value == null ) {
//            return null;
//        }
//        return ((Map<String, Object>) this.value).get( name );
//    }

//    @SuppressWarnings("unchecked")
//    public Object get(int index) {
//        if ( !isArray() ) {
//            throw new UnsupportedOperationException( "JSON is not an array" );
//        }
//        if ( value == null ) {
//            return null;
//        }
//        return ((List<Object>) value).get( index );
//    }

}
