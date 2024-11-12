package com.ank.japi.validation.error;

/**
 * Check value is less than min.
 */
public class MinimumOverflowError
        extends NumberError {

    public static final int    CODE   = 10102;
    public static final String REASON =
            "The value passed in is less than the minimum value.";

    private MinimumOverflowError(int code, String reason, String guide) {
        super( code, reason, guide );
    }

    public static Builder<MinimumOverflowError> builder() {
        return new Builder<>( CODE, REASON ) {
            @Override
            protected MinimumOverflowError newInstance(
                    int code, String reason, String guide
            ) {
                return new MinimumOverflowError( code, reason, guide );
            }
        };
    }
}
