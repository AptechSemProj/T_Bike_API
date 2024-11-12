package com.ank.japi.std;

import com.ank.japi.Handleable;
import com.ank.japi.Response;
import com.ank.japi.ResponseBuilder;
import com.ank.japi.ResponseConfigurer;
import com.ank.japi.validation.ValidationError;

import java.util.function.Function;

public abstract class StdResponseBuilder<T extends Handleable, R>
        implements ResponseBuilder<T, R> {

    protected abstract Response<R> createResponse(
            int statusCode, String message, R data
    );

    public static final int OK                              = 200;
    public static final int CREATED                         = 201;
    public static final int ACCEPTED                        = 202;
    public static final int NO_CONTENT                      = 204;
    public static final int RESET_CONTENT                   = 205;
    public static final int PARTIAL_CONTENT                 = 206;
    public static final int MOVED_PERMANENTLY               = 301;
    public static final int FOUND                           = 302;
    public static final int SEE_OTHER                       = 303;
    public static final int NOT_MODIFIED                    = 304;
    public static final int USE_PROXY                       = 305;
    public static final int TEMPORARY_REDIRECT              = 307;
    public static final int BAD_REQUEST                     = 400;
    public static final int UNAUTHORIZED                    = 401;
    public static final int PAYMENT_REQUIRED                = 402;
    public static final int FORBIDDEN                       = 403;
    public static final int NOT_FOUND                       = 404;
    public static final int METHOD_NOT_ALLOWED              = 405;
    public static final int NOT_ACCEPTABLE                  = 406;
    public static final int PROXY_AUTHENTICATION_REQUIRED   = 407;
    public static final int REQUEST_TIMEOUT                 = 408;
    public static final int CONFLICT                        = 409;
    public static final int GONE                            = 410;
    public static final int LENGTH_REQUIRED                 = 411;
    public static final int PRECONDITION_FAILED             = 412;
    public static final int REQUEST_ENTITY_TOO_LARGE        = 413;
    public static final int REQUEST_URI_TOO_LONG            = 414;
    public static final int UNSUPPORTED_MEDIA_TYPE          = 415;
    public static final int REQUESTED_RANGE_NOT_SATISFIABLE = 416;
    public static final int EXPECTATION_FAILED              = 417;
    public static final int UNPROCESSABLE_ENTITY            = 418;
    public static final int LOCKED                          = 419;
    public static final int FAILED_DEPENDENCY               = 420;
    public static final int INTERNAL_SERVER_ERROR           = 500;
    public static final int NOT_IMPLEMENTED                 = 501;
    public static final int BAD_GATEWAY                     = 502;
    public static final int SERVICE_UNAVAILABLE             = 503;
    public static final int GATEWAY_TIMEOUT                 = 504;
    public static final int HTTP_VERSION_NOT_SUPPORTED      = 505;

    protected final ResponseConfigurer configurer;
    private final   T                  handleable;

    protected StdResponseBuilder(ResponseConfigurer configurer, T handleable) {
        this.handleable = handleable;
        if ( configurer == null ) {
            throw new NullPointerException( "configurer is null" );
        }
        this.configurer = configurer;
    }

//    @Override
//    public Map<String, List<String>> headers() {
//        return null;
//    }

    @Override
    public Response<R> ok(Function<T, R> func) {
        return ok( func.apply( handleable ) );
    }

    @Override
    public Response<R> ok(R data) {
        return createResponse( OK, "OK", data );
    }

    @Override
    public Response<R> created(Function<T, R> func) {
        return created( func.apply( handleable ) );
    }

    @Override
    public Response<R> created(R data) {
        return createResponse( CREATED, "CREATED", data );
    }

    @Override
    public Response<R> noContent() {
        return createResponse( NO_CONTENT, "NO CONTENT", null );
    }

    @Override
    public Response<R> notFound(String message) {
        return createResponse( NOT_FOUND, "NOT FOUND", null );
    }

    @Override
    public Response<R> conflict(String message) {
        return createResponse( CONFLICT, message, null );
    }

    @Override
    public Response<R> internalServerError(String message) {
        return createResponse( INTERNAL_SERVER_ERROR, message, null );
    }

    @Override
    public Response<R> error(ValidationError error) {
        return configurer.getBoundResponse( error );
    }

    @Override
    public Response<R> throwable(Throwable throwable) {
        return configurer.getBoundResponse( throwable );
    }
}
