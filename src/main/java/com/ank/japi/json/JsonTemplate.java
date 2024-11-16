package com.ank.japi.json;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class JsonTemplate {

    protected abstract Set<JsonField> configure();

    private final Map<String, JsonField> template;

    public JsonTemplate() {
        Set<JsonField> fields = configure();
        if ( fields != null ) {
            this.template = fields
                    .parallelStream()
                    .map( f -> Map.entry( f.getName(), f ) )
                    .collect(
                            Collectors.toUnmodifiableMap(
                                    Map.Entry::getKey,
                                    Map.Entry::getValue
                            )
                    );
        }
        else {
            this.template = null;
        }
    }

    public static JsonTemplate copy(JsonTemplate template) {
        return new JsonTemplate() {
            @Override
            protected Set<JsonField> configure() {
                return template.configure();
            }
        };
    }

    public final JsonObject createJsonObject() {
        return new JsonObject( template, false );
    }

    public final boolean isConfigured() {
        return template != null;
    }
}
