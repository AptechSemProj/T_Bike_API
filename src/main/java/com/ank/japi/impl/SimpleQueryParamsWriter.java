package com.ank.japi.impl;

import com.ank.japi.QueryParamsWriter;
import com.ank.japi.validation.Requirement;
import com.ank.japi.validation.Validators;

public final class SimpleQueryParamsWriter
        implements QueryParamsWriter {

    private final Validators validators;

    public SimpleQueryParamsWriter(Validators validators) {
        this.validators = validators;
    }

    @Override
    public void set(String name, Object value, Requirement... requirements) {
        validators.bind( name, value ).require( requirements );
    }

//	@Override
//	public void setByte(String name, Object value, Requirement... requirements) {
//
//	}
//
//	@Override
//	public void setShort(String name, Object value, Requirement... requirements) {
//
//	}
//
//	@Override
//	public void setFloat(String name, Object value, Requirement... requirements) {
//
//	}
//
//	@Override
//	public void setDouble(String name, Object value, Requirement... requirements) {
//
//	}
//
//	@Override
//	public void setBigDecimal(String name, Object value, Requirement... requirements) {
//
//	}
//
//	@Override
//	public void setBigInteger(String name, Object value, Requirement... requirements) {
//
//	}
//
//	@Override
//	public void setArray(String name, Object value, Requirement... requirements) {
//
//	}
//
//	@Override
//	public void setStringArray(String name, Object value, Requirement... requirements) {
//
//	}
//
//	@Override
//	public void setBooleanArray(String name, Object value, Requirement... requirements) {
//
//	}
//
//	@Override
//	public void setBooleanArray(String name, Object value, boolean exact, Requirement... requirements) {
//
//	}
//
//	@Override
//	public void setByteArray(String name, Object value, Requirement... requirements) {
//
//	}
//
//	@Override
//	public void setShortArray(String name, Object value, Requirement... requirements) {
//
//	}
//
//	@Override
//	public void setIntArray(String name, Object value, Requirement... requirements) {
//
//	}
//
//	@Override
//	public void setLongArray(String name, Object value, Requirement... requirements) {
//
//	}
//
//	@Override
//	public void setFloatArray(String name, Object value, Requirement... requirements) {
//
//	}
//
//	@Override
//	public void setDoubleArray(String name, Object value, Requirement... requirements) {
//
//	}
//
//	@Override
//	public void setBigDecimalArray(String name, Object value, Requirement... requirements) {
//
//	}
//
//	@Override
//	public void setBigIntegerArray(String name, Object value, Requirement... requirements) {
//
//	}
}
