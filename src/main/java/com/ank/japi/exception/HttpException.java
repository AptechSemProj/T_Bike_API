package com.ank.japi.exception;

import lombok.Getter;

@Getter
public class HttpException
        extends RuntimeException {

    private final int statusCode;

    public HttpException(int statusCode, String message) {
        super( message );
        this.statusCode = statusCode;
    }
}
