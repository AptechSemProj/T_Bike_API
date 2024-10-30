package se.pj.tbike.io;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {
	OK( 200, "Ok" ),
	CREATED( 201, "Created" ),
	NO_CONTENT( 204, "No content" ),
	BAD_REQUEST( 400, "Bad Request" ),
	NOT_FOUND( 404, "Not found" ),
	INTERNAL_SERVER_ERROR( 500, "Internal Server Error" ),
	;

	private final int code;

	private final String message;
}