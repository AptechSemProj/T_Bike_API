package se.pj.tbike.io;

import lombok.Getter;
import se.pj.tbike.util.json.DynamicJson;
import se.pj.tbike.util.result.ResultPage;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Response<T extends ResponseType>
		implements DynamicJson {

	private final int status;

	private final String message;

	private final T data;

	public Response(Status status, T data, String message) {
		this.status = status.getCode();
		this.message = message != null ? message : status.getMessage();
		this.data = data;
	}

	public Response(Status status, T data) {
		this( status, data, status.getMessage() );
	}

	public Response(Status status) {
		this( status, null );
	}

	//********************* Fast Way Create Pagination *********************//

	@Deprecated
	public static <T extends ResponseType>
	Response<Arr<T>> pagination(ResultPage<T> page, String message) {
		return new Pagination<>( page, message );
	}

	@Deprecated
	public static <T extends ResponseType>
	Response<Arr<T>> pagination(ResultPage<T> page) {
		return new Pagination<>( page );
	}

	// status: 200 - OK

	public static <T extends ResponseType>
	Response<T> ok(T data, String message) {
		return new Response<>( Status.OK, data, message );
	}

	public static <T extends ResponseType>
	Response<T> ok(T data) {
		return new Response<>( Status.OK, data );
	}

	// status: 201 - CREATED

	public static <T extends ResponseType>
	Response<T> created(T data, String message) {
		return new Response<>( Status.CREATED, data, message );
	}

	public static <T extends ResponseType>
	Response<T> created(T data) {
		return new Response<>( Status.CREATED, data );
	}

	// status: 204 - NO CONTENT

	public static <T extends ResponseType>
	Response<T> noContent(String message) {
		return new Response<>( Status.NO_CONTENT, null, message );
	}

	public static <T extends ResponseType>
	Response<T> noContent() {
		return new Response<>( Status.NO_CONTENT, null );
	}

	// 400

	public static <T extends ResponseType>
	Response<T> badRequest(String message) {
		return new Response<>( Status.BAD_REQUEST, null, message );
	}

	public static <T extends ResponseType>
	Response<T> badRequest() {
		return new Response<>( Status.BAD_REQUEST, null );
	}

	// 404

	public static <T extends ResponseType>
	Response<T> notFound(String message) {
		return new Response<>( Status.NOT_FOUND, null, message );
	}

	public static <T extends ResponseType> Response<T> notFound() {
		return new Response<>( Status.NOT_FOUND, null );
	}

	// 500

	public static <T extends ResponseType>
	Response<T> internalServerError(String message) {
		return new Response<>( Status.INTERNAL_SERVER_ERROR, null, message );
	}


	public static <T extends ResponseType>
	Response<T> internalServerError() {
		return new Response<>( Status.INTERNAL_SERVER_ERROR, null );
	}

	//********************* Implements DynamicJson *********************//

	@Override
	public Map<String, Object> toJson() {
		final Map<String, Object> json = new HashMap<>();
		json.put( "status", status );
		json.put( "message", message );
		if ( data != null ) {
			json.put( "data", Val.wrap( data ).get() );
		}
		return json;
	}
}
