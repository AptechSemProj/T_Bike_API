package com.ank.japi;

/**
 * @param <T> response type
 */
public interface ResponseBuilder<T> {

//    Map<String, List<String>> headers();

    /* 1xx */

    /* 2xx */

    Response<T> ok(T data);

    default Response<T> ok() {
        return ok( null );
    }

    Response<T> created(T data);

    default Response<T> created() {
        return created( null );
    }

    Response<T> noContent();

    /* 3xx */
    /* 4xx */

    default Response<T> badRequest() {
        return badRequest( null );
    }

    Response<T> badRequest(String message);

    default Response<T> notFound() {
        return notFound( null );
    }

    Response<T> notFound(String message);

    default Response<T> conflict() {
        return conflict( null );
    }

    Response<T> conflict(String message);

    /* 5xx */

    default Response<T> internalServerError() {
        return internalServerError( null );
    }

    Response<T> internalServerError(String message);

}
