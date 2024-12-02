package com.ank.japi.impl;

import com.ank.japi.HttpStatus;
import com.ank.japi.Response;
import com.ank.japi.ResponseBuilder;

@Deprecated
public abstract class StdResponseBuilder
        implements ResponseBuilder {

    protected abstract Response createResponse(
            int statusCode, String message, Object data
    );

    @Override
    public Response ok(Object data, String message) {
        return createResponse(HttpStatus.OK, message, data);
    }

    @Override
    public Response created(Object data, String message) {
        return createResponse(HttpStatus.CREATED, message, data);
    }

    @Override
    public Response noContent(String message) {
        return createResponse(HttpStatus.NO_CONTENT, message, null);
    }
}
