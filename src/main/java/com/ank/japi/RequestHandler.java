package com.ank.japi;

import com.ank.japi.validation.Validators;

import java.util.function.Consumer;

public interface RequestHandler<RQ extends Request<RP>, RP> {

    RequestHandler<RQ, RP> setQueryParams(Consumer<QueryParamsWriter> func);

    Response<RP> handle(RQ req, Exec1<RP, RQ> exec);

    Response<RP> handle(RQ req, Exec2<RP, RQ> exec);

    Response<RP> handle(
            RQ req,
            Exec1<RP, RQ> exec,
            Exec3<RP, RQ> requestValidate
    );

    Response<RP> handle(
            RQ req,
            Exec2<RP, RQ> exec,
            Exec3<RP, RQ> requestValidate
    );

    @FunctionalInterface
    interface Exec1<RP, RQ extends Request<RP>> {

        Response<RP> apply(ResponseBuilder<RP> res, RQ req) throws Throwable;

    }

    @FunctionalInterface
    interface Exec2<RP, RQ extends Request<RP>> {

        Response<RP> apply(
                ResponseBuilder<RP> res, RQ req, QueryParamsReader query
        ) throws Throwable;

    }

    @FunctionalInterface
    interface Exec3<RP, RQ extends Request<RP>> {

        void apply(RQ req, Validators validators) throws Throwable;

    }
}
