package com.ank.japi.validation.error;

import com.ank.japi.validation.ValidationError;

public abstract class NumberError
        extends ValidationError {
    protected NumberError(int code, String reason, String guide) {
        super( code, reason, guide );
    }
}
