package com.ank.japi.validation.error;

import com.ank.japi.validation.ValidationError;

public class UnexpectedValueError
        extends ValidationError {

    public static final int    CODE   = 10051;
    public static final String REASON =
            "The value passed in is an unexpected value.";

    private UnexpectedValueError(int code, String reason, String guide) {
        super( code, reason, guide );
    }

    public static Builder<UnexpectedValueError> builder() {
        return new Builder<>( CODE, REASON ) {
            @Override
            protected UnexpectedValueError newInstance(
                    int code, String reason, String guide
            ) {
                return new UnexpectedValueError( code, reason, guide );
            }
        };
    }
}
