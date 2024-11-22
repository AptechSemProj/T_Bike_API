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
}
