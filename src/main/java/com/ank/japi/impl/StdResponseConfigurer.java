package com.ank.japi.impl;

import com.ank.japi.Response;
import com.ank.japi.ResponseConfigurer;
import com.ank.japi.validation.error.UnknownError;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

@Deprecated
public abstract class StdResponseConfigurer
        implements ResponseConfigurer {

    private final Map<Class<?>, Function<Throwable, Response>>
            errorHandlers;

    public StdResponseConfigurer() {
        this.errorHandlers = new LinkedHashMap<>();
    }

    @Override
    public <E extends Throwable>
    ResponseConfigurer setResponseBinding(
            Class<E> clazz,
            Function<E, ? extends Response> constructor
    ) {
        @SuppressWarnings("unchecked")
        var c = (Function<Throwable, Response>) constructor;
        this.errorHandlers.put(clazz, c);
        return this;
    }

    @Override
    public Response getBoundResponse(Throwable t) {
        var constructor = errorHandlers.get(t.getClass());
        if (constructor == null) {
            String msg = "No response is bound to ["
                    + t.getClass().getSimpleName()
                    + "]. Message: " + t.getMessage();
            throw UnknownError.builder().reason(msg).build();
        }
        return constructor.apply(t);
    }
}
