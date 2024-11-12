package com.ank.japi.json;

import java.util.Map;

public enum JsonType {
    STRING,
    NUMBER,
    OBJECT,
    ARRAY,
    BOOLEAN,
    ANY,
    ;

    public static JsonType fromJavaType(Class<?> javaType) {
        if ( CharSequence.class.isAssignableFrom( javaType ) ) {
            return STRING;
        }
        if ( Number.class.isAssignableFrom( javaType ) ) {
            return NUMBER;
        }
        if ( Map.class.isAssignableFrom( javaType ) ) {
            return OBJECT;
        }
        if ( Iterable.class.isAssignableFrom( javaType ) ) {
            return ARRAY;
        }
        if ( Boolean.class.isAssignableFrom( javaType ) ) {
            return BOOLEAN;
        }
        return ANY;
    }
}
