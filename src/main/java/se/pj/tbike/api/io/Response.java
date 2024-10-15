package se.pj.tbike.api.io;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.pj.tbike.util.json.DynamicJson;
import se.pj.tbike.util.result.ResultPage;

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

	protected Response( Status status, T data ) {
		this( status.getCode(), data, status.getMessage(), null, null );
	}

	protected Response( Status status, T data, List<String> errors ) {
		this( status.getCode(), data, status.getMessage(), errors, null );
	}

	protected Response( Status status, T data, Map<String, String> errors ) {
		this( status.getCode(), data, status.getMessage(), null, errors );
	}

	protected Response( Status status, T data, String message ) {
		this( status.getCode(), data, message, null, null );
	}

	protected Response( Status status, T data, String message,
	                    List<String> errors ) {
		this( status.getCode(), data, message, errors, null );
	}

	protected Response( Status status, T data, String message,
	                    Map<String, String> errors ) {
		this( status.getCode(), data, message, null, errors );
	}

	protected Response( int status, T data, String message ) {
		this( status, data, message, null, null );
	}

	protected Response( int status, T data, String message,
	                    List<String> errors ) {
		this( status, data, message, errors, null );
	}

	protected Response( int status, T data, String message,
	                    Map<String, String> errors ) {
		this( status, data, message, null, errors );
	}


	//********************* Fast Way Create Pagination *********************//

	public static <T extends ResponseType>
	Response<Arr<T>> pagination( ResultPage<T> page, String message ) {
		return new Pagination<>( page, message );
	}

	public static <T extends ResponseType>
	Response<Arr<T>> pagination( ResultPage<T> page ) {
		return new Pagination<>( page );
	}

	// 200

	public static <T extends ResponseType>
	Response<T> ok( T data, String message ) {
		return new Response<>( Status.OK, data, message );
	}

	public static <T extends ResponseType>
	Response<T> ok( T data ) {
		return new Response<>( Status.OK, data );
	}

	// 201

	public static <T extends ResponseType>
	Response<T> created( T data, String message ) {
		return new Response<>( Status.CREATED, data, message );
	}

	public static <T extends ResponseType>
	Response<T> created( T data ) {
		return new Response<>( Status.CREATED, data );
	}

	// 204

	public static <T extends ResponseType>
	Response<T> noContent( String message ) {
		return new Response<>( Status.NO_CONTENT, null, message );
	}

	public static <T extends ResponseType>
	Response<T> noContent() {
		return new Response<>( Status.NO_CONTENT, null );
	}

	// 400

	public static <T extends ResponseType>
	Response<T> badRequest( String message ) {
		return new Response<>( Status.BAD_REQUEST, null, message );
	}

	public static <T extends ResponseType>
	Response<T> badRequest() {
		return new Response<>( Status.BAD_REQUEST, null );
	}

	// 404

	public static <T extends ResponseType>
	Response<T> notFound( String message ) {
		return new Response<>( Status.NOT_FOUND, null, message );
	}

	public static <T extends ResponseType> Response<T> notFound() {
		return new Response<>( Status.NOT_FOUND, null );
	}

	//********************* Implements DynamicJson *********************//

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
