package com.ank.japi;

import com.ank.japi.validation.Validators;

public interface RequestHandler<H extends Handleable, I extends Request<H>, O> {

    RequestHandler<H, I, O> validateParams(QueryValidator func);

    RequestHandler<H, I, O> validateBody(BodyValidator<H> func);

    Response<O> handle(Exec1<H, O> exec);

    Response<O> handle(Exec2<H, O> exec);

    Response<O> handle(Exec3<H, O> exec);

    Response<O> handle(Exec4<H, O> exec);

    @FunctionalInterface
    interface Exec1<T extends Handleable, R> {

        Response<R> apply(ResponseBuilder<T, R> rb) throws Throwable;

    }

    @FunctionalInterface
    interface Exec2<T extends Handleable, R> {

        Response<R> apply(ResponseBuilder<T, R> rb, T t) throws Throwable;

    }

    @FunctionalInterface
    interface Exec3<T extends Handleable, R> {

        Response<R> apply(ResponseBuilder<T, R> rb, QueryParamsReader r)
        throws Throwable;

    }

    @FunctionalInterface
    interface Exec4<T extends Handleable, R> {

        Response<R> apply(ResponseBuilder<T, R> rb, T t, QueryParamsReader r)
        throws Throwable;

    }

    @FunctionalInterface
    interface QueryValidator {

        void accept(QueryParamsWriter w) throws Throwable;

    }

    @FunctionalInterface
    interface BodyValidator<T extends Handleable> {

        void accept(T t, Validators v) throws Throwable;

    }
}
