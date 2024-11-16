package com.ank.japi.impl;

import com.ank.japi.QueryParams;
import com.ank.japi.QueryParamsWriter;
import com.ank.japi.Request;
import com.ank.japi.RequestHandler;
import com.ank.japi.Response;
import com.ank.japi.ResponseBuilder;
import com.ank.japi.ResponseConfigurer;
import com.ank.japi.validation.Validators;
import com.ank.japi.validation.error.UnknownError;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class StdRequestHandler<RQ extends Request<RP>, RP>
        implements RequestHandler<RQ, RP> {

    private final ResponseConfigurer<RP> responseConfigurer;
    private final ResponseBuilder<RP>    responseBuilder;
    private final QueryParams            queryParams;

    public StdRequestHandler(
            ResponseConfigurer<RP> responseConfigurer,
            QueryParams queryParams
    ) {
        if ( responseConfigurer == null ) {
            throw new IllegalArgumentException( "responseConfigurer is null" );
        }
        if ( queryParams == null ) {
            throw new IllegalArgumentException( "queryParams is null" );
        }
        this.responseConfigurer = responseConfigurer;
        this.responseBuilder = responseConfigurer.getResponseBuilder();
        this.queryParams = queryParams;
    }

    public StdRequestHandler(ResponseConfigurer<RP> responseConfigurer) {
        this( responseConfigurer, new SimpleQueryParams() );
    }

    public StdRequestHandler(Supplier<ResponseConfigurer<RP>> supplier) {
        this( supplier.get() );
    }

    @Override
    public RequestHandler<RQ, RP> setQueryParams(
            Consumer<QueryParamsWriter> func
    ) {
        func.accept( queryParams.getWriter() );
        return this;
    }

    @Override
    public Response<RP> handle(RQ req, Exec1<RP, RQ> exec) {
        return handle( req, (res, body, params) -> exec.apply( res, body ) );
    }

    @Override
    public Response<RP> handle(RQ req, Exec2<RP, RQ> exec) {
        try {
            if ( exec == null ) {
                throw UnknownError.builder().build();
            }
            return exec.apply( responseBuilder, req, queryParams.getReader() );
        }
        catch ( Throwable e ) {
            return responseConfigurer.getBoundResponse( e );
        }
    }

    @Override
    public Response<RP> handle(
            RQ req,
            Exec2<RP, RQ> exec,
            Exec3<RP, RQ> requestValidate
    ) {
        try {
            if ( requestValidate == null || exec == null ) {
                throw UnknownError.builder().build();
            }
            Validators validators = new Validators();
            requestValidate.apply( req, validators );
            validators.validate();
            return exec.apply( responseBuilder, req, queryParams.getReader() );
        }
        catch ( Throwable e ) {
            return responseConfigurer.getBoundResponse( e );
        }
    }
}
