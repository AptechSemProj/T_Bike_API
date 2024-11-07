package se.pj.tbike.api.util;

@FunctionalInterface
public interface Executor<R> {

	void apply(Response.Builder<?, R> res);

	@FunctionalInterface
	interface WithAllArgs<B extends Handleable, R> {

		void apply(Response.Builder<B, R> res, B body, QueryParams queryParams);

	}

	@FunctionalInterface
	interface WithBody<B extends Handleable, R> {

		void apply(Response.Builder<B, R> res, B body);

	}

	@FunctionalInterface
	interface WithQueryParams<R> {

		void apply(Response.Builder<?, R> res, QueryParams queryParams);

	}
}
