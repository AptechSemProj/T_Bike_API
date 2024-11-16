package com.ank.japi;

import com.ank.japi.json.JsonTemplate;

import java.util.function.Function;

public interface ResponseConfigurer<T> {

    ResponseBuilder<T> getResponseBuilder();

    ResponseConfigurer<T> configureJsonTemplate(JsonTemplate template);

    JsonTemplate getConfiguredTemplate();

    <E extends Throwable>
    ResponseConfigurer<T> setResponseBinding(
            Class<E> throwable, Function<E, Response<T>> constructor
    );

    Response<T> getBoundResponse(Throwable throwable);

}
