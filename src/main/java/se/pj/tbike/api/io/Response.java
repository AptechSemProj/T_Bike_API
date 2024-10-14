package se.pj.tbike.api.io;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.pj.tbike.util.json.DynamicJson;

public class Response<T extends ResponseType> implements DynamicJson {

	private final int status;

	private final String message;

	private final T data;

	private final List<String> errorsList;

	private final Map<String, String> errorsMap;

	private Response( int status, T data, String message,
	                  List<String> errorsList,
	                  Map<String, String> errorsMap ) {
		this.status = status;
		this.data = data;
		this.message = message;
		this.errorsList = errorsList;
		this.errorsMap = errorsMap;
	}

	protected Response( int status, T data, String message,
	                    Map<String, String> errors ) {
		this( status, data, message, null, errors );
	}

	protected Response( int status, T data, String message,
	                    List<String> errors ) {
		this( status, data, message, errors, null );
	}

	protected Response( int status, T data, String message ) {
		this( status, data, message, null, null );
	}

	public static <T extends ResponseType> Response<T> created(
			T data, String message ) {
		return new Response<>( 201, data, message );
	}

	public static <T extends ResponseType> Response<T> created( T data ) {
		return created( data, Message.CREATED.getValue() );
	}

	public static <T extends ResponseType> Response<T> ok(
			T data, String message ) {
		return new Response<>( 200, data, message );
	}

	public static <T extends ResponseType> Response<T> ok( T data ) {
		return ok( data, Message.OK.getValue() );
	}

	public static <T extends ResponseType> Response<T> noContent(
			String message ) {
		return new Response<>( 204, null, message );
	}

	public static <T extends ResponseType> Response<T> noContent() {
		return noContent( Message.NOT_FOUND.getValue() );
	}

	public static <T extends ResponseType> Response<T> notFound(
			String message ) {
		return new Response<>( 404, null, message );
	}

	public static <T extends ResponseType> Response<T> notFound() {
		return notFound( Message.NOT_FOUND.getValue() );
	}

	@Override
	public Map<String, Object> toJson() {
		final Map<String, Object> json = new HashMap<>();
		json.put( "status", status );
		json.put( "message", message );
		// data
		if ( data != null ) {
			if ( data instanceof Val<?> val )
				json.put( "data", val.get() );
			else
				json.put( "data", data );
		}
		// errors
		if ( errorsList != null && !errorsList.isEmpty() )
			json.put( "errors", errorsList );
		else if ( errorsMap != null && !errorsMap.isEmpty() )
			json.put( "errors", errorsMap );
		return json;
	}
}
