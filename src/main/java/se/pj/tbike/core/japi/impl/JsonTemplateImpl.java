package se.pj.tbike.core.japi.impl;

import com.ank.japi.json.*;

import java.util.HashSet;
import java.util.Set;

public final class JsonTemplateImpl
        extends JsonTemplate {

    public static final JsonTemplate INSTANCE = new JsonTemplateImpl();

    public static final String STATUS = "status";
    public static final String MESSAGE = "message";
    public static final String DATA = "data";
    public static final String METADATA = "metadata";

    @Override
    protected Set<JsonField> configure() {
        return new HashSet<>() {{
            add(new JsonNumberField(STATUS, false).acceptOnlyInteger());
            add(new JsonStringField(MESSAGE));
            add(new JsonAnyField(DATA));
            add(new JsonObjectField(METADATA, Set.of(
                    new JsonNumberField("total_elements", false),
                    new JsonNumberField("total_pages", false),
                    new JsonNumberField("page_size", false),
                    new JsonNumberField("current_page", false),
                    new JsonNumberField("next"),
                    new JsonNumberField("previous")
            )));
        }};
    }
}
