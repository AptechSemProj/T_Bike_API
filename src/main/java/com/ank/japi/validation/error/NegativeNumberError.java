package com.ank.japi.validation.error;

public class NegativeNumberError
        extends NumberError {

    public static final int    CODE   = 10103;
    public static final String REASON =
            "The value passed in is a negative number.";

    private NegativeNumberError(int code, String reason, String guide) {
        super( code, reason, guide );
    }

    public static Builder<NegativeNumberError> builder() {
        return new Builder<>( CODE, REASON ) {
            @Override
            protected NegativeNumberError newInstance(
                    int code, String reason, String guide
            ) {
                return new NegativeNumberError( code, reason, guide );
            }
        };
    }
}
