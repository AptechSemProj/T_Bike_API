package com.ank.japi.impl;

import com.ank.japi.Response;
import com.ank.japi.ResponseBuilder;

public abstract class StdResponseBuilder<T>
        implements ResponseBuilder<T> {

    protected abstract Response<T> createResponse(
            int statusCode, String message, T data
    );

    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int ACCEPTED = 202;
    public static final int NO_CONTENT = 204;
    public static final int RESET_CONTENT = 205;
    public static final int PARTIAL_CONTENT = 206;
    public static final int MOVED_PERMANENTLY = 301;
    public static final int FOUND = 302;
    public static final int SEE_OTHER = 303;
    public static final int NOT_MODIFIED = 304;
    public static final int USE_PROXY = 305;
    public static final int TEMPORARY_REDIRECT = 307;
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int PAYMENT_REQUIRED = 402;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int METHOD_NOT_ALLOWED = 405;
    public static final int NOT_ACCEPTABLE = 406;
    public static final int PROXY_AUTHENTICATION_REQUIRED = 407;
    public static final int REQUEST_TIMEOUT = 408;
    public static final int CONFLICT = 409;
    public static final int GONE = 410;
    public static final int LENGTH_REQUIRED = 411;
    public static final int PRECONDITION_FAILED = 412;
    public static final int REQUEST_ENTITY_TOO_LARGE = 413;
    public static final int REQUEST_URI_TOO_LONG = 414;
    public static final int UNSUPPORTED_MEDIA_TYPE = 415;
    public static final int REQUESTED_RANGE_NOT_SATISFIABLE = 416;
    public static final int EXPECTATION_FAILED = 417;
    public static final int UNPROCESSABLE_ENTITY = 418;
    public static final int LOCKED = 419;
    public static final int FAILED_DEPENDENCY = 420;
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int NOT_IMPLEMENTED = 501;
    public static final int BAD_GATEWAY = 502;
    public static final int SERVICE_UNAVAILABLE = 503;
    public static final int GATEWAY_TIMEOUT = 504;
    public static final int HTTP_VERSION_NOT_SUPPORTED = 505;

//    @Override
//    public Map<String, List<String>> headers() {
//        return null;
//    }

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
