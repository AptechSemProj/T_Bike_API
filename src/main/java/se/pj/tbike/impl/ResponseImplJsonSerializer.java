package se.pj.tbike.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

class ResponseImplJsonSerializer
        extends StdSerializer<ResponseImpl<?>> {

    public ResponseImplJsonSerializer() {
        this(null);
    }

    protected ResponseImplJsonSerializer(Class<ResponseImpl<?>> t) {
        super(t);
    }

    @Override
    public void serialize(ResponseImpl<?> resp, JsonGenerator gen,
                          SerializerProvider provider) throws IOException {
        gen.writeObject(resp.toJson());
    }
}
