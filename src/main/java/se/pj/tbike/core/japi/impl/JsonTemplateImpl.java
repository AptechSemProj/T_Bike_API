package se.pj.tbike.core.japi.impl;

import com.ank.japi.json.Any;
import com.ank.japi.json.JsonField;
import com.ank.japi.json.JsonNumber;
import com.ank.japi.json.JsonString;
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
        return new JsonNumber( "status", false )
                .acceptOnlyInteger();
    }

    public JsonField messageField() {
        return new JsonString( "message", true );
    }

    public JsonField bodyField() {
        return new Any( "data", true );
    }
}
