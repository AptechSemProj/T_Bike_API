package com.ank.japi.std;

import com.ank.japi.Handleable;
import com.ank.japi.QueryParamsReader;
import com.ank.japi.QueryParamsWriter;
import com.ank.japi.Request;
import com.ank.japi.RequestConfigurer;
import com.ank.japi.RequestHandler;
import com.ank.japi.Response;
import com.ank.japi.ResponseBuilder;
import com.ank.japi.ResponseConfigurer;
import com.ank.japi.validation.ValidationError;
import com.ank.japi.validation.Validators;

import java.util.concurrent.atomic.AtomicReference;

public class StdRequestHandler<H extends Handleable, I extends Request<H>, O>
        implements RequestHandler<H, I, O> {

    private final RequestConfigurer<I>  requestConfigurer;
    private final ResponseBuilder<H, O> responseBuilder;
    private final H                     body;
    private       QueryParamsReader     queryParams = null;
    private       Response<O>           response    = null;

    public StdRequestHandler(
            RequestConfigurer<I> reqConf,
            ResponseConfigurer resConf
    ) {
        if ( reqConf == null ) {
            throw new IllegalArgumentException( "reqConf is null" );
        }
        if ( resConf == null ) {
            throw new IllegalArgumentException( "resConf is null" );
        }
        this.requestConfigurer = reqConf;
        this.body = reqConf.getRequestBody().toHandleableObject();
        this.responseBuilder = resConf.getResponseBuilder( this.body );
    }

    @Override
    public RequestHandler<H, I, O> validateParams(QueryValidator func) {
        if ( response != null ) {
            return this;
        }
        try {
            QueryParamsWriter writer = requestConfigurer
                    .getQueryParamsWriter();
            func.accept( writer );
            queryParams = writer.getReader();
        }
        catch ( Throwable e ) {
            response = responseBuilder.throwable( e );
        }
        return this;
    }

    @Override
    public RequestHandler<H, I, O> validateBody(BodyValidator<H> func) {
        if ( response != null ) {
            return this;
        }
        try {
            Validators validators = new Validators();
            func.accept( this.body, validators );
            var err = new AtomicReference<ValidationError>( null );
            validators.validate( err::set );
            ValidationError e = err.get();
            if ( e != null ) {
                response = responseBuilder.error( e );
            }
        }
        catch ( Throwable e ) {
            response = responseBuilder.throwable( e );
        }
        return this;
    }

    @Override
    public Response<O> handle(Exec1<H, O> exec) {
        return handle( (res, body, params) -> exec.apply( res ) );
    }

    @Override
    public Response<O> handle(Exec2<H, O> exec) {
        return handle( (res, body, params) -> exec.apply( res, body ) );
    }

    @Override
    public Response<O> handle(Exec3<H, O> exec) {
        return handle( (res, body, params) -> exec.apply( res, params ) );
    }

    @Override
    public Response<O> handle(Exec4<H, O> exec) {
        if ( response != null ) {
            return response;
        }
        try {
            return exec.apply( responseBuilder, body, queryParams );
        }
        catch ( Throwable e ) {
            return responseBuilder.throwable( e );
        }
    }
}
