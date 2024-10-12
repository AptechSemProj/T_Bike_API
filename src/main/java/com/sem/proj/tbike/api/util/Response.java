package com.sem.proj.tbike.api.util;

import java.util.HashMap;
import java.util.Map;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@JsonSerialize( using = Response.Serializer.class )
public class Response<T> {

	@JsonProperty( index = 0, required = true, access = Access.READ_ONLY )
	private final int status;

	private final String message;

	private final T data;

	public static <T> Response<T> ok( T data ) {
		return new Response<>( 200, Message.OK.value, data );
	}

	public static <T> Response<T> notFound() {
		return new Response<>( 404, Message.NOT_FOUND.value, null );
	}

	public Map<String, Object> toJson() {
		return new HashMap<>() {{
			put( "status", status );
			put( "message", message );
			put( "data", data );
		}};
	}

	@AllArgsConstructor
	@Getter( AccessLevel.PACKAGE )
	public enum Message {
		OK( "Ok" ),
		NO_CONTENT( "No content" ),
		NOT_FOUND( "Not found" ),
		;
		private final String value;
	}

	static class Serializer
			extends StdSerializer<Response<?>> {

		public Serializer() {
			this( null );
		}

		protected Serializer( Class<Response<?>> t ) {
			super( t );
		}

		@Override
		public void serialize( Response<?> value,
		                       JsonGenerator gen,
		                       SerializerProvider provider )
				throws IOException {
			gen.writeObject( value.toJson() );
		}
	}
}
