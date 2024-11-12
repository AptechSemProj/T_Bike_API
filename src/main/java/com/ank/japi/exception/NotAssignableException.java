package com.ank.japi.exception;

public final class NotAssignableException
        extends RuntimeException {

    public NotAssignableException() {
        super();
    }

    public NotAssignableException(String message) {
        super( message );
    }

    public NotAssignableException(String message, Throwable cause) {
        super( message, cause );
    }

    public NotAssignableException(Throwable cause) {
        super( cause );
    }
}
