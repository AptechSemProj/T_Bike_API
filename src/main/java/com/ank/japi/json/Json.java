package com.ank.japi.json;

import java.io.Serializable;

public interface Json
        extends JsonField.Value, Serializable {

    default boolean isObject() {
        return false;
    }

    default boolean isArray() {
        return false;
    }

    default boolean isStructured() {
        return isObject() || isArray();
    }
}
