package se.pj.tbike.api.io;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter( AccessLevel.PACKAGE )
public enum Message {
	CREATED( "Created" ),
	OK( "Ok" ),
	NO_CONTENT( "No content" ),
	NOT_FOUND( "Not found" ),
	;
	private final String value;
}
