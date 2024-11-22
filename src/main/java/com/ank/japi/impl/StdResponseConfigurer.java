package com.ank.japi.impl;

import com.ank.japi.HttpStatus;
import com.ank.japi.Response;
import com.ank.japi.ResponseConfigurer;
import com.ank.japi.validation.error.UnknownError;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class StdResponseConfigurer<T>
        implements ResponseConfigurer<T> {

    private final Map<Class<?>, Function<Throwable, Response<T>>>
            errorHandlers;

    public StdResponseConfigurer() {
        this.errorHandlers = new LinkedHashMap<>();
        setResponseBinding(UnknownError.class, t -> new Response<>() {
            @Override
            public T getResponseBody() {
                return null;
            }

            @Override
            public int getStatusCode() {
                return HttpStatus.NOT_IMPLEMENTED;
            }
        });
    }

    @Override
    public <E extends Throwable>
    ResponseConfigurer<T> setResponseBinding(
            Class<E> clazz,
            Function<E, Response<T>> constructor
    ) {
        @SuppressWarnings("unchecked")
        var c = (Function<Throwable, Response<T>>) constructor;
        this.errorHandlers.put(clazz, c);
        return this;
    }

    @Override
    public Response<T> getBoundResponse(Throwable t) {
        var constructor = errorHandlers.get(t.getClass());
        if (constructor == null) {
            String msg = "No response is bound to " + t.getClass().getName();
            throw UnknownError.builder().reason(msg).build();
        }
        return constructor.apply(t);
    }
}
