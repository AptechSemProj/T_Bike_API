package com.ank.japi.impl;

import com.ank.japi.Response;
import com.ank.japi.ResponseBuilder;

import static com.ank.japi.HttpStatus.BAD_REQUEST;
import static com.ank.japi.HttpStatus.CONFLICT;
import static com.ank.japi.HttpStatus.CREATED;
import static com.ank.japi.HttpStatus.INTERNAL_SERVER_ERROR;
import static com.ank.japi.HttpStatus.NOT_FOUND;
import static com.ank.japi.HttpStatus.NO_CONTENT;
import static com.ank.japi.HttpStatus.OK;

public abstract class StdResponseBuilder<T>
        implements ResponseBuilder<T> {

    protected abstract Response<T> createResponse(
            int statusCode, String message, T data
    );

    @Override
    public Response<T> ok(T data, String message) {
        return createResponse(OK, message, data);
    }

    @Override
    public Response<T> created(T data, String message) {
        return createResponse(CREATED, message, data);
    }

    @Override
    public Response<T> noContent(String message) {
        return createResponse(NO_CONTENT, message, null);
    }

    @Override
    public Response<T> badRequest(String message) {
        return createResponse(BAD_REQUEST, message, null);
    }

    @Override
    public Response<T> notFound(String message) {
        return createResponse(NOT_FOUND, message, null);
    }

    @Override
    public Response<T> conflict(String message) {
        return createResponse(CONFLICT, message, null);
    }

    @Override
    public Response<T> internalServerError(String message) {
        return createResponse(INTERNAL_SERVER_ERROR, message, null);
    }
}
