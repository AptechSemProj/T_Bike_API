package com.ank.japi.validation.error;

import com.ank.japi.validation.ValidationError;

public class UnexpectedTypeError
        extends ValidationError {

    public static final int    CODE   = 10050;
    public static final String REASON = "Unexpected data type.";

    private UnexpectedTypeError(int code, String reason, String guide) {
        super( code, reason, guide );
    }

    public static Builder<UnexpectedTypeError> builder() {
        return new Builder<>( CODE, REASON ) {
            @Override
            protected UnexpectedTypeError newInstance(
                    int code, String reason, String guide
            ) {
                return new UnexpectedTypeError( code, reason, guide );
            }
        };
    }
}
