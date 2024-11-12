package com.ank.japi.json;

import java.util.NoSuchElementException;

public interface JsonField {

    interface Value {

        Object get() throws NoSuchElementException;

        void set(Object value) throws NotAssignableException;

        final class NotAssignableException
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
    }

    String getName();

    JsonType getJsonType();

    boolean isAssignable(Object value);

    boolean isNullable();

    Value value();

    boolean equals(Object o);

    int hashCode();

}
