package se.pj.tbike.api.util;

import se.pj.tbike.validation.Requirement;
import se.pj.tbike.validation.Validators;

import java.util.function.BiConsumer;

public class StdRequestHandler<H extends Handleable,
		B extends RequestObject<H>,
		R extends ResponseObject<R, H>>
		implements RequestHandler<H, B, R> {
	@Override
	public RequestHandler<H, B, R> validateParam(Object param, Requirement... requirements) {
		return null;
	}

	@Override
	public void validateBody(BiConsumer<B, Validators> validator) {

	}

	@Override
	public void handle(B b, BiConsumer<H, Response<H>> handler) {

	}

	@Override
	public Response<R> getResponse() {
		return null;
	}
}
