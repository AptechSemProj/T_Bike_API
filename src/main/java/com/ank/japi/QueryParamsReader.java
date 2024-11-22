package com.ank.japi;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface QueryParamsReader {

    default Object get(String name) {
        return get( name, null );
    }

    Object get(String name, Object defaultValue);

    default String getString(String name) {
        return String.valueOf( get( name ) );
    }

    default String getString(String name, String defaultValue) {
        return String.valueOf( get( name, defaultValue ) );
    }

    Boolean getBoolean(String name);

    default boolean getBoolean(String name, boolean defaultValue) {
        Boolean b = getBoolean( name );
        return b == null ? defaultValue : b;
    }

    Byte getByte(String name);

    default byte getByte(String name, byte defaultValue) {
        Byte b = getByte( name );
        return b == null ? defaultValue : b;
    }

    Short getShort(String name);

    default short getShort(String name, short defaultValue) {
        Short s = getShort( name );
        return s == null ? defaultValue : s;
    }

    Integer getInt(String name);

    default int getInt(String name, int defaultValue) {
        Integer i = getInt( name );
        return i == null ? defaultValue : i;
    }

    Long getLong(String name);

    default long getLong(String name, long defaultValue) {
        Long l = getLong( name );
        return l == null ? defaultValue : l;
    }

    Float getFloat(String name);

    default float getFloat(String name, float defaultValue) {
        Float f = getFloat( name );
        return f == null ? defaultValue : f;
    }

    Double getDouble(String name);

    default double getDouble(String name, double defaultValue) {
        Double d = getDouble( name );
        return d == null ? defaultValue : d;
    }

    BigDecimal getBigDecimal(String name);

    default BigDecimal getBigDecimal(String name, BigDecimal defaultValue) {
        BigDecimal b = getBigDecimal( name );
        return b == null ? defaultValue : b;
    }

    BigInteger getBigInteger(String name);

    default BigInteger getBigInteger(String name, BigInteger defaultValue) {
        BigInteger b = getBigInteger( name );
        return b == null ? defaultValue : b;
    }
}
