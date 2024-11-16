package se.pj.tbike.core.japi.impl;

import com.ank.japi.json.JsonAnyField;
import com.ank.japi.json.JsonField;
import com.ank.japi.json.JsonNumberField;
import com.ank.japi.json.JsonStringField;
import com.ank.japi.json.JsonTemplate;

import java.util.HashSet;
import java.util.Set;

public final class JsonTemplateImpl
        extends JsonTemplate {

    public static final JsonTemplate INSTANCE = new JsonTemplateImpl();

    public static final String STATUS  = "status";
    public static final String MESSAGE = "message";
    public static final String DATA    = "data";

    @Override
    protected Set<JsonField> configure() {
        return new HashSet<>() {{
            add( new JsonNumberField( STATUS, false ).acceptOnlyInteger() );
            add( new JsonStringField( MESSAGE, true ) );
            add( new JsonAnyField( DATA, true ) );
        }};
    }
}
