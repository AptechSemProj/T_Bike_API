package se.pj.tbike.api.util;

import se.pj.tbike.validation.Requirement;
import se.pj.tbike.validation.Validators;

import java.util.function.BiConsumer;

public interface RequestHandler<H extends Handleable,
		B extends RequestObject<H>,
		R extends ResponseObject<R, H>> {

	/* Phase 1: Validation */
	RequestHandler<H, B, R> validateParam(Object param,
	                                      Requirement... requirements);

	void validateBody(BiConsumer<B, Validators> validator);

	/* Phase 2: Handle */
	void handle(B b, BiConsumer<H, Response<H>> handler);

	/* Phase 3: Create response */
	Response<R> getResponse();

}
