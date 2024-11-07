package se.pj.tbike.validation.error;

import se.pj.tbike.validation.Error;

public abstract class NumberError extends Error {
	protected NumberError(int code, String reason, String guide) {
		super( code, reason, guide );
	}

	protected NumberError(int code, String reason) {
		super( code, reason );
	}
}
