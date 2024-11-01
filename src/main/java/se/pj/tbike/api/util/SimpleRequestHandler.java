package se.pj.tbike.api.util;

import org.springframework.http.ResponseEntity;
import se.pj.tbike.api.util.Executor.WithAll;
import se.pj.tbike.api.util.Executor.WithBody;
import se.pj.tbike.api.util.Executor.WithParams;
import se.pj.tbike.api.util.Response.Builder;
import se.pj.tbike.validation.Validators;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class SimpleRequestHandler<H extends Handleable, I extends Request<H>, O>
		implements RequestHandler<H, I, O> {

	@Override
	public RequestHandler<H, I, O> validateParams(Consumer<Validators> validators) {
		return this;
	}

	@Override
	public RequestHandler<H, I, O> validateBody(BiConsumer<I, Validators> validator) {
		return this;
	}

	protected void handle(Builder<H, O> res, I body, Params params) {

	}

	@Override
	public RequestHandler<H, I, O> handle(Executor<O> executor) {
		return this;
	}

	@Override
	public RequestHandler<H, I, O> handle(WithAll<H, O> executor,
	                                      I body) {
		return this;
	}

	@Override
	public RequestHandler<H, I, O> handle(WithBody<H, O> executor,
	                                      I body) {
		return this;
	}

	@Override
	public RequestHandler<H, I, O> handle(WithParams<O> executor) {
		return this;
	}

	@Override
	public ResponseEntity<O> getResponse() {
		return null;
	}
}