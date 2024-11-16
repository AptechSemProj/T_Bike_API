package se.pj.tbike.core.japi.impl;

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
import org.springframework.web.client.HttpClientErrorException;

import java.util.function.Function;

public class ResponseConfigurerImpl<T>
        extends StdResponseConfigurer<T> {

    public ResponseConfigurerImpl() {
        configureJsonTemplate( JsonTemplateImpl.INSTANCE )
                .setResponseBinding(
                        AlreadyExistsError.class,
                        response( HttpStatus.CONFLICT )
                )
                .setResponseBinding(
                        MaximumOverflowError.class,
                        response( HttpStatus.BAD_REQUEST )
                )
                .setResponseBinding(
                        MinimumOverflowError.class,
                        response( HttpStatus.BAD_REQUEST )
                )
                .setResponseBinding(
                        NegativeNumberError.class,
                        response( HttpStatus.BAD_REQUEST )
                )
                .setResponseBinding(
                        NoContentError.class,
                        response( HttpStatus.BAD_REQUEST )
                )
                .setResponseBinding(
                        NotExistError.class,
                        response( HttpStatus.NOT_FOUND )
                )
                .setResponseBinding(
                        NumberFormatError.class,
                        response( HttpStatus.BAD_REQUEST )
                )
                .setResponseBinding(
                        PositiveNumberError.class,
                        response( HttpStatus.BAD_REQUEST )
                )
                .setResponseBinding(
                        UnexpectedValueError.class,
                        response( HttpStatus.BAD_REQUEST )
                )
                .setResponseBinding(
                        UnexpectedTypeError.class,
                        response( HttpStatus.BAD_REQUEST )
                )
                .setResponseBinding(
                        UnknownError.class,
                        response( HttpStatus.INTERNAL_SERVER_ERROR )
                )
                .setResponseBinding(
                        HttpClientErrorException.BadRequest.class,
                        response( HttpStatus.BAD_REQUEST )
                )
                .setResponseBinding(
                        HttpClientErrorException.Unauthorized.class,
                        response( HttpStatus.UNAUTHORIZED )
                )
                .setResponseBinding(
                        HttpClientErrorException.Forbidden.class,
                        response( HttpStatus.FORBIDDEN )
                )
                .setResponseBinding(
                        HttpClientErrorException.NotFound.class,
                        response( HttpStatus.NOT_FOUND )
                )
                .setResponseBinding(
                        HttpClientErrorException.Conflict.class,
                        response( HttpStatus.CONFLICT )
                )
                .setResponseBinding(
                        HttpException.class,
                        e -> response( HttpStatus.valueOf( e.getStatusCode() ) )
                                .apply( e )
                );
    }

    private <E extends Throwable>
    Function<E, Response<T>> response(HttpStatus status) {
        return throwable -> new ResponseImpl<>(
                getConfiguredTemplate(), status, throwable.getMessage()
        );
    }

    @Override
    public ResponseBuilder<T> getResponseBuilder() {
        return new StdResponseBuilder<>() {
            @Override
            protected Response<T> createResponse(
                    int statusCode, String message, T data
            ) {
                return new ResponseImpl<>(
                        getConfiguredTemplate(),
                        statusCode, message, data
                );
            }
        };
    }
}