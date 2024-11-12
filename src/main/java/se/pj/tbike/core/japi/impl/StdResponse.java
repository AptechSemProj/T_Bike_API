package se.pj.tbike.core.japi.impl;

import com.ank.japi.json.Json;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.http.HttpStatus;
import com.ank.japi.json.JsonTemplate;
import com.ank.japi.Response;

@JsonSerialize(using = StdResponseJsonSerializer.class)
public abstract class StdResponse<T>
        implements Response<T> {

    protected abstract JsonTemplate template();

    protected final JsonTemplate template;
    private final   T            data;
    private final   int          statusCode;
    private final   String       message;

    public StdResponse(int statusCode, String message, T data) {
        this.template = template();
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public StdResponse(HttpStatus status, T data) {
        this( status.value(), status.getReasonPhrase(), data );
    }

    protected Object toJson() {
        if ( template.isConfigured() ) {
            Json json = template.createJson();
            json.set( "status", statusCode );
            json.set( "message", message );
            json.set( "data", data );
            return json.get();
        }
        else {
            return data;
        }
    }

    @Override
    public T getResponseBody() {
        return data;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }
}
