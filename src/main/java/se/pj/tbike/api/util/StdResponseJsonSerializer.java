package se.pj.tbike.api.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.util.Map;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;

class StdResponseJsonSerializer
		extends StdSerializer<StdResponse<?>> {
	public StdResponseJsonSerializer() {
		this( null );
	}

	protected StdResponseJsonSerializer(Class<StdResponse<?>> t) {
		super( t );
	}

	@Override
	public void serialize(StdResponse<?> value,
	                      JsonGenerator gen,
	                      SerializerProvider provider)
			throws IOException {
		Object json = value.toJson();
		gen.writeObject( json );
//		if ( json == null ) {
//			gen.writeNull();
//			return;
//		} else if ( json instanceof Boolean bool ) {
//			gen.writeBoolean( bool );
//			return;
//		} else if ( json instanceof String str ) {
//			try {
//				byte[] utf8Bytes = str.getBytes( StandardCharsets.UTF_8 );
//				StandardCharsets.UTF_8.newDecoder()
//						.decode( ByteBuffer.wrap( utf8Bytes ) );
//				gen.writeUTF8String( utf8Bytes, 0, utf8Bytes.length );
//			} catch ( MalformedInputException e ) {
//				gen.writeString( str );
//			}
//		} else if ( json instanceof Integer ) {
//
//		}
//		gen.writeNull();
//		gen.writeBoolean(  );
//		gen.writeString(  );
//		gen.writeUTF8String(  );
//		gen.writeNumber(  );
//		gen.writeArray(  );
//		gen.writeBinary(  );
//		gen.writeObject(  );
//		gen.writePOJO(  );
//		else if ( json instanceof  ) {
//
//		}
//		if ( json instanceof Map<?, ?> map ) {
//			gen.writeObject( map );
//		}
	}
}
