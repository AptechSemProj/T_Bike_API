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

    @Override
    protected Set<JsonField> configure() {
        return new HashSet<>() {{
            add( statusField() );
            add( messageField() );
            add( bodyField() );
        }};
    }

    public JsonField statusField() {
        return new JsonNumberField( "status", false )
                .acceptOnlyInteger();
    }

    public JsonField messageField() {
        return new JsonStringField( "message", true );
    }

    public JsonField bodyField() {
        return new JsonAnyField( "data", true );
    }
}
