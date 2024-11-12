package com.ank.japi.std;

import com.ank.japi.Handleable;
import com.ank.japi.Response;
import com.ank.japi.ResponseBuilder;
import com.ank.japi.ResponseConfigurer;
import com.ank.japi.json.JsonTemplate;
import com.ank.japi.validation.ValidationError;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class StdResponseConfigurer
        implements ResponseConfigurer {

    private final Map<Class<?>, Function<Throwable, Response<?>>>
                               errorHandlers;
    private final Function<JsonTemplate, ResponseBuilder<?, ?>>
                               builderInstance;
    private       JsonTemplate jsonTemplate;

    public StdResponseConfigurer(
            Function<JsonTemplate, ResponseBuilder<?, ?>> builderInstance
    ) {
        this.errorHandlers = new LinkedHashMap<>();
        this.builderInstance = builderInstance;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Handleable, R> ResponseBuilder<T, R>
    getResponseBuilder(T t) {
        return (ResponseBuilder<T, R>) builderInstance.apply( jsonTemplate );
    }

    @Override
    public ResponseConfigurer configureJsonTemplate(JsonTemplate template) {
        this.jsonTemplate = template;
        return this;
    }

    @Override
    public JsonTemplate getConfiguredTemplate() {
        return jsonTemplate;
    }

    @Override
    public ResponseConfigurer setResponseBinding(
            Class<? extends Throwable> clazz,
            Function<Throwable, Response<?>> constructor
    ) {
        this.errorHandlers.put( clazz, constructor );
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Response<T> getBoundResponse(ValidationError error) {
        return (Response<T>) errorHandlers
                .get( error.getClass() )
                .apply( error );
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Response<T> getBoundResponse(Throwable throwable) {
        return (Response<T>) errorHandlers
                .get( throwable.getClass() )
                .apply( throwable );
    }
}
