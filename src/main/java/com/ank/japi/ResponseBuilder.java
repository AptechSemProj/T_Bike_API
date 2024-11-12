package com.ank.japi;

import com.ank.japi.validation.ValidationError;

import java.util.function.Function;

public interface ResponseBuilder<H extends Handleable, T> {

//    Map<String, List<String>> headers();

    /* 1xx */

    /* 2xx */

    Response<T> ok(Function<H, T> func);

    Response<T> ok(T data);

    default Response<T> ok() {
        return ok( (T) null );
    }

    Response<T> created(Function<H, T> func);

    Response<T> created(T data);

    default Response<T> created() {
        return created( (T) null );
    }

    Response<T> noContent();

    /* 3xx */
    /* 4xx */

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

    /* extra */

    default Response<T> error(ValidationError.Builder<?> err) {
        return error( err.build() );
    }

    Response<T> error(ValidationError err);

    Response<T> throwable(Throwable throwable);
}
