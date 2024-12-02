package com.ank.japi;

import com.ank.japi.validation.Validators;

import java.util.function.Consumer;

@Deprecated
public interface RequestHandler<RQ, RP> {

    RequestHandler<RQ, RP> setQueryParams(Consumer<QueryParamsWriter> func);

    Response handle(RQ req, Exec1<RQ> exec);

    Response handle(RQ req, Exec2<RQ> exec);

    Response handle(
            RQ req,
            Exec1<RQ> exec,
            Exec3<RQ> requestValidate
    );

    Response handle(
            RQ req,
            Exec2<RQ> exec,
            Exec3<RQ> requestValidate
    );

    @FunctionalInterface
    interface Exec1<RQ> {

        Response apply(ResponseBuilder res, RQ req) throws Throwable;

    }

    @FunctionalInterface
    interface Exec2<RQ> {

        Response apply(ResponseBuilder res, RQ req, QueryParamsReader query)
                throws Throwable;

    }

    @FunctionalInterface
    interface Exec3<RQ> {

        void apply(RQ req, Validators validators) throws Throwable;

    }
}
