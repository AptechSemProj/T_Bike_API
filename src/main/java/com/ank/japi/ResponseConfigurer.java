package com.ank.japi;

import java.util.function.Function;

@Deprecated
public interface ResponseConfigurer {

    ResponseBuilder getResponseBuilder();

    <E extends Throwable>
    ResponseConfigurer setResponseBinding(
            Class<E> throwable, Function<E, ? extends Response> constructor
    );

    Response getBoundResponse(Throwable throwable);

}
