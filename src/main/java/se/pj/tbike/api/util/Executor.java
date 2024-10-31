package se.pj.tbike.api.util;

@FunctionalInterface
public interface Executor<R> {

	void apply(Response.Builder<? extends Handleable, R> res);

	@FunctionalInterface
	interface WithAll<B extends Handleable, R> {

		void apply(Response.Builder<B, R> res,
		           B body,
		           Params params);

	}

	@FunctionalInterface
	interface WithBody<B extends Handleable, R> {

		void apply(Response.Builder<B, R> res,
		           B body);

	}

	@FunctionalInterface
	interface WithParams<R> {

		void apply(Response.Builder<? extends Handleable, R> res,
		           Params params);

	}
}
