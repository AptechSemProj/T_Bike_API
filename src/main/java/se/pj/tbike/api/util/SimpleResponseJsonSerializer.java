package se.pj.tbike.api.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

class SimpleResponseJsonSerializer
		extends StdSerializer<SimpleResponse<?>> {
	public SimpleResponseJsonSerializer() {
		this( null );
	}

	protected SimpleResponseJsonSerializer(Class<SimpleResponse<?>> t) {
		super( t );
	}

	@Override
	public void serialize(SimpleResponse<?> value,
	                      JsonGenerator gen,
	                      SerializerProvider provider)
			throws IOException {
		gen.writeObject( value.toJson() );
	}
}
