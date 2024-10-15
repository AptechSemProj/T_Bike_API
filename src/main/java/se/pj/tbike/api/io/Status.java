package se.pj.tbike.api.io;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter( AccessLevel.PACKAGE )
public enum Status {
	OK( 200, "Ok" ),
	CREATED( 201, "Created" ),
	NO_CONTENT( 204, "No content" ),
	BAD_REQUEST( 400, "Bad Request" ),
	NOT_FOUND( 404, "Not found" ),
	;

	private final int code;

	private final String message;
}
