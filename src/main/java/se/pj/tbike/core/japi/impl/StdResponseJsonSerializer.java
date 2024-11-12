package se.pj.tbike.core.japi.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

class StdResponseJsonSerializer
        extends StdSerializer<StdResponse<?>> {

    public StdResponseJsonSerializer() {
        this( null );
    }

    protected StdResponseJsonSerializer(Class<StdResponse<?>> t) {
        super( t );
    }

    @Override
    public void serialize(
            StdResponse<?> resp, JsonGenerator gen, SerializerProvider provider
    )
    throws IOException {
        gen.writeObject( resp.toJson() );
    }
}
