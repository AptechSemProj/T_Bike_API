package com.ank.japi.validation.error;

import com.ank.japi.validation.ValidationError;

public class NoContentError
        extends ValidationError {

    public static final int    CODE   = 12040;
    public static final String REASON =
            "The value passed in is null, empty or has no content.";

    private NoContentError(int code, String reason, String guide) {
        super( code, reason, guide );
    }

    public static Builder<NoContentError> builder() {
        return new Builder<>( CODE, REASON ) {
            @Override
            protected NoContentError newInstance(
                    int code, String reason, String guide
            ) {
                return new NoContentError( code, reason, guide );
            }
        };
    }
}
