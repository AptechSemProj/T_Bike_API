package com.ank.japi;

/**
 * @param <T> response type
 */
public interface ResponseBuilder<T> {

//    Map<String, List<String>> headers();

    /* 1xx */

    /* 2xx */

    Response<T> ok(T data, String message);

    default Response<T> ok(T data) {
        return ok(data, "Ok");
    }

    default Response<T> ok() {
        return ok(null);
    }

    Response<T> created(T data, String message);

    default Response<T> created(T data) {
        return created(data, "Created");
    }

    default Response<T> created() {
        return created(null);
    }

    Response<T> noContent(String message);

    default Response<T> noContent() {
        return noContent("No Content");
    }

    /* 3xx */
    /* 4xx */

    Response<T> badRequest(String message);

    default Response<T> badRequest() {
        return badRequest("Bad Request");
    }

    Response<T> notFound(String message);

    default Response<T> notFound() {
        return notFound("Not Found");
    }

    Response<T> conflict(String message);

    default Response<T> conflict() {
        return conflict("Conflict");
    }

    /* 5xx */

    Response<T> internalServerError(String message);

    default Response<T> internalServerError() {
        return internalServerError("Internal Server Error");
    }

}
