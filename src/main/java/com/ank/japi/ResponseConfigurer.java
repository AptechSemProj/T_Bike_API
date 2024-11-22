package com.ank.japi;

import java.util.function.Function;

public interface ResponseConfigurer<T> {

    ResponseBuilder<T> getResponseBuilder();

    <E extends Throwable>
    ResponseConfigurer<T> setResponseBinding(
            Class<E> throwable, Function<E, Response<T>> constructor
    );

    Response<T> getBoundResponse(Throwable throwable);

}
