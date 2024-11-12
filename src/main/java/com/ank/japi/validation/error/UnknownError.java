package com.ank.japi.validation.error;

import com.ank.japi.validation.ValidationError;

public class UnknownError
        extends ValidationError {

    public static final int    CODE   = 10000;
    public static final String REASON = "Unknown error.";

    private UnknownError(int code, String reason, String guide) {
        super( code, reason, guide );
    }

    public static Builder<UnknownError> builder() {
        return new Builder<>( CODE, REASON ) {
            @Override
            protected UnknownError newInstance(
                    int code, String reason, String guide
            ) {
                return new UnknownError( code, reason, guide );
            }
        };
    }
}
