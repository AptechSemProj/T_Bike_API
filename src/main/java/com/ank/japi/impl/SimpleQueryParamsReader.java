package com.ank.japi.impl;

import com.ank.japi.QueryParamsReader;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

public final class SimpleQueryParamsReader
        implements QueryParamsReader {

    private final Map<String, Object> params;

    public SimpleQueryParamsReader(Map<String, Object> params) {
        this.params = Map.copyOf(params);
    }

    @Override
    public Object get(String name, Object defaultValue) {
        Object value = params.get(name);
        return value == null ? defaultValue : value;
    }

    @Override
    public Boolean getBoolean(String key) {
        return get(key) instanceof Boolean b ? b : null;
    }

    @Override
    public Byte getByte(String key) {
        return get(key) instanceof Byte b ? b : null;
    }

    @Override
    public Short getShort(String key) {
        return get(key) instanceof Short s ? s : null;
    }

    @Override
    public Integer getInt(String key) {
        return get(key) instanceof Integer i ? i : null;
    }

    @Override
    public Long getLong(String key) {
        return get(key) instanceof Long l ? l : null;
    }

    @Override
    public Float getFloat(String key) {
        return get(key) instanceof Float f ? f : null;
    }

    @Override
    public Double getDouble(String key) {
        return get(key) instanceof Double d ? d : null;
    }

    @Override
    public BigDecimal getBigDecimal(String key) {
        return get(key) instanceof BigDecimal bd ? bd : null;
    }

    @Override
    public BigInteger getBigInteger(String key) {
        return get(key) instanceof BigInteger bi ? bi : null;
    }
}
