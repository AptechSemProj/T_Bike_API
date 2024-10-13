package com.sem.proj.tbike.api.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@JsonSerialize( using = Response.Serializer.class )
public class Response<T> {

	@JsonProperty( index = 0, required = true )
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

	@Getter
	@Setter
	public static class Pagination<E>
			extends Response<Collection<E>> {

		private final Metadata metadata;

		public Pagination( String message,
		                   Collection<E> data,
		                   Metadata metadata ) {
			super( 200, message, data );
			this.metadata = metadata;
		}

		public Pagination( Message message,
		                   Collection<E> data,
		                   Metadata metadata ) {
			this( message.getValue(), data, metadata );
		}

		@Override
		public Map<String, Object> toJson() {
			Map<String, Object> json = super.toJson();
			json.put( "metadata", metadata );
			return json;
		}

		@Getter
		public static class Metadata {

			@JsonProperty( "total_elements" )
			private final int totalElements;

			@JsonProperty( "total_pages" )
			private final int totalPages;

			@JsonProperty( "page_size" )
			private final int size;

			@JsonProperty( "current_page" )
			private final int current;

			private final Integer next, previous;

			public Metadata( int totalElements, int current, int size ) {
				if ( current < 0 )
					throw new IllegalArgumentException();
				if ( size < 0 )
					throw new IllegalArgumentException();
				if ( totalElements < 0 )
					throw new IllegalArgumentException();

				this.totalElements = totalElements;
				this.totalPages = calcPages( totalElements, size );
				this.size = size;
				this.current = current;
				this.next = ( current + 1 ) < totalPages ? current + 1 : null;
				this.previous = current > 0 ? current - 1 : null;
			}

			private static int calcPages( int elements, int size ) {
				if ( size > elements )
					throw new IllegalArgumentException();
				int pages = elements / size;
				return elements % size > 0 ? pages + 1 : pages;
			}
		}
	}
}
