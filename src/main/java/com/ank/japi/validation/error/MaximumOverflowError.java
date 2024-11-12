package com.ank.japi.validation.error;

/**
 * The value is greater than max.
 */
public class MaximumOverflowError
        extends NumberError {

    public static final int    CODE   = 10101;
    public static final String REASON =
            "The value passed is greater than the maximum value.";

    private MaximumOverflowError(int code, String reason, String guide) {
        super( code, reason, guide );
    }

    public static Builder<MaximumOverflowError> builder() {
        return new Builder<>( CODE, REASON ) {
            @Override
            protected MaximumOverflowError newInstance(
                    int code, String reason, String guide
            ) {
                return new MaximumOverflowError( code, reason, guide );
            }
        };
    }
}
