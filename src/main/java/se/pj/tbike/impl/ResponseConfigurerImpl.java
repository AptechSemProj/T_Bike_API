package se.pj.tbike.impl;

import com.ank.japi.Response;
import com.ank.japi.ResponseBuilder;
import com.ank.japi.exception.HttpException;
import com.ank.japi.impl.StdResponseBuilder;
import com.ank.japi.impl.StdResponseConfigurer;
import com.ank.japi.validation.error.AlreadyExistsError;
import com.ank.japi.validation.error.MaximumOverflowError;
import com.ank.japi.validation.error.MinimumOverflowError;
import com.ank.japi.validation.error.NegativeNumberError;
import com.ank.japi.validation.error.NoContentError;
import com.ank.japi.validation.error.NotExistError;
import com.ank.japi.validation.error.NumberFormatError;
import com.ank.japi.validation.error.PositiveNumberError;
import com.ank.japi.validation.error.UnexpectedTypeError;
import com.ank.japi.validation.error.UnexpectedValueError;
import com.ank.japi.validation.error.UnknownError;
import org.springframework.http.HttpStatus;

import java.util.function.Function;

public class ResponseConfigurerImpl<T>
        extends StdResponseConfigurer<T> {

    public ResponseConfigurerImpl() {
        setResponseBinding(
                AlreadyExistsError.class,
                response(HttpStatus.CONFLICT)
        )
                .setResponseBinding(
                        MaximumOverflowError.class,
                        response(HttpStatus.BAD_REQUEST)
                )
                .setResponseBinding(
                        MinimumOverflowError.class,
                        response(HttpStatus.BAD_REQUEST)
                )
                .setResponseBinding(
                        NegativeNumberError.class,
                        response(HttpStatus.BAD_REQUEST)
                )
                .setResponseBinding(
                        NoContentError.class,
                        response(HttpStatus.BAD_REQUEST)
                )
                .setResponseBinding(
                        NotExistError.class,
                        response(HttpStatus.NOT_FOUND)
                )
                .setResponseBinding(
                        NumberFormatError.class,
                        response(HttpStatus.BAD_REQUEST)
                )
                .setResponseBinding(
                        PositiveNumberError.class,
                        response(HttpStatus.BAD_REQUEST)
                )
                .setResponseBinding(
                        UnexpectedValueError.class,
                        response(HttpStatus.BAD_REQUEST)
                )
                .setResponseBinding(
                        UnexpectedTypeError.class,
                        response(HttpStatus.BAD_REQUEST)
                )
                .setResponseBinding(
                        UnknownError.class,
                        response(HttpStatus.INTERNAL_SERVER_ERROR)
                )
                .setResponseBinding(
                        HttpException.class,
                        e -> response(
                                HttpStatus.valueOf(e.getStatusCode())
                        ).apply(e)
                );
    }

    private <E extends Throwable>
    Function<E, Response<T>> response(HttpStatus status) {
        return throwable -> ResponseImpl.status(status)
                .message(throwable.getLocalizedMessage())
                .build(null);
    }

    @Override
    public ResponseBuilder<T> getResponseBuilder() {
        return new StdResponseBuilder<>() {
            @Override
            protected Response<T> createResponse(
                    int statusCode, String message, T data
            ) {
                return ResponseImpl.status(statusCode)
                        .message(message)
                        .build(data);
            }
        };
    }
}
