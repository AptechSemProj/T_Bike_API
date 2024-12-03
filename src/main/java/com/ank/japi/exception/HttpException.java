package com.ank.japi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class HttpException
        extends RuntimeException {

    private final int statusCode;

    public HttpException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpException(HttpStatusCode statusCode, String message) {
        super(message);
        if (!statusCode.isError()) {
            throw internalServerError("Status code is not an error code.");
        }
        this.statusCode = statusCode.value();
    }

    public static HttpException notFound(String message) {
        return new HttpException(HttpStatus.NOT_FOUND, message);
    }

    public static HttpException badRequest(String message) {
        return new HttpException(HttpStatus.BAD_REQUEST, message);
    }

    public static HttpException unauthorized(String message) {
        return new HttpException(HttpStatus.UNAUTHORIZED, message);
    }

    public static HttpException unauthorized() {
        return unauthorized(HttpStatus.UNAUTHORIZED.getReasonPhrase());
    }

    public static HttpException forbidden(String message) {
        return new HttpException(HttpStatus.FORBIDDEN, message);
    }

    public static HttpException notAcceptable(String message) {
        return new HttpException(HttpStatus.NOT_ACCEPTABLE, message);
    }

    public static HttpException notImplemented(String message) {
        return new HttpException(HttpStatus.NOT_IMPLEMENTED, message);
    }

    public static HttpException internalServerError(String message) {
        return new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
