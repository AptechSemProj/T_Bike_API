package se.pj.tbike.api.util;

import org.springframework.http.ResponseEntity;
import se.pj.tbike.api.util.Executor.WithAllArgs;
import se.pj.tbike.api.util.Executor.WithBody;
import se.pj.tbike.api.util.Executor.WithQueryParams;
import se.pj.tbike.validation.Validators;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface RequestHandler<H extends Handleable, I extends Request<H>, O> {

	RequestHandler<H, I, O> validateParams(Consumer<Validators> validators);

	RequestHandler<H, I, O> validateBody(BiConsumer<I, Validators> validator);

	RequestHandler<H, I, O> handle(Executor<O> executor);

	RequestHandler<H, I, O> handle(WithAllArgs<H, O> executor, I body);

	RequestHandler<H, I, O> handle(WithBody<H, O> executor, I body);

	RequestHandler<H, I, O> handle(WithQueryParams<O> executor);

	ResponseEntity<O> getResponse();

}
