package com.ank.japi;

import com.ank.japi.json.JsonAnyField;
import com.ank.japi.json.JsonField;
import com.ank.japi.json.JsonTemplate;
import com.ank.japi.validation.ValidationError;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface ResponseConfigurer {

    <T extends Handleable, R> ResponseBuilder<T, R> getResponseBuilder(T t);

    default ResponseConfigurer configureJsonTemplate(String... fields) {
        return configureJsonTemplate( new JsonTemplate() {
            @Override
            protected Set<JsonField> configure() {
                if ( fields == null ) {
                    return null;
                }
                else if ( fields.length == 0 ) {
                    return new HashSet<>();
                }
                return Set.of( fields )
                          .parallelStream()
                          .map( JsonAnyField::new )
                          .collect( Collectors.toSet() );
            }
        } );
    }

    default ResponseConfigurer configureJsonTemplate(JsonField... fields) {
        return configureJsonTemplate( new JsonTemplate() {
            @Override
            protected Set<JsonField> configure() {
                if ( fields == null ) {
                    return null;
                }
                else if ( fields.length == 0 ) {
                    return new HashSet<>();
                }
                return new HashSet<>( Set.of( fields ) );
            }
        } );
    }

    ResponseConfigurer configureJsonTemplate(JsonTemplate template);

    JsonTemplate getConfiguredTemplate();

    ResponseConfigurer setResponseBinding(
            Class<? extends Throwable> throwable,
            Function<Throwable, Response<?>> constructor
    );

    <T> Response<T> getBoundResponse(ValidationError error);

    <T> Response<T> getBoundResponse(Throwable throwable);

}
