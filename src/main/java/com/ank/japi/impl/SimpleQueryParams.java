package com.ank.japi.impl;

import com.ank.japi.QueryParams;
import com.ank.japi.QueryParamsReader;
import com.ank.japi.QueryParamsWriter;
import com.ank.japi.validation.Validators;

public final class SimpleQueryParams
        implements QueryParams {

    private final Validators validators;

    public SimpleQueryParams() {
        validators = new Validators();
    }

    @Override
    public QueryParamsReader getReader() {
        return new SimpleQueryParamsReader( validators.validate() );
    }

    @Override
    public QueryParamsWriter getWriter() {
        return new SimpleQueryParamsWriter( validators );
    }
}
