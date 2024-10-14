package se.pj.tbike.util.json;

import java.util.Map;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

// package-private
class DynamicJsonSerializer extends StdSerializer<DynamicJson> {

	public DynamicJsonSerializer() {
		this( null );
	}

	protected DynamicJsonSerializer( Class<DynamicJson> t ) {
		super( t );
	}

	@Override
	public void serialize( DynamicJson value,
	                       JsonGenerator gen,
	                       SerializerProvider provider )
			throws IOException {
		Map<String, Object> json = value.toJson();
		gen.writeObject( json == null ? Map.of() : json );
	}
}
