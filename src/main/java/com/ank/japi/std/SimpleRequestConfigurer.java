package com.ank.japi.std;

import com.ank.japi.QueryParamsWriter;
import com.ank.japi.Request;
import com.ank.japi.RequestConfigurer;
import com.ank.japi.validation.Validators;

public class SimpleRequestConfigurer<T extends Request<?>>
        implements RequestConfigurer<T> {

    private final T requestBody;

    public SimpleRequestConfigurer(T body) {
        this.requestBody = body;
    }

    @Override
    public QueryParamsWriter getQueryParamsWriter() {
        return new SimpleQueryParamsWriter( new Validators() );
    }

    @Override
    public T getRequestBody() {
        return requestBody;
    }
}
