package se.pj.tbike.core.japi.impl;

import com.ank.japi.json.JsonObject;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.http.HttpStatus;
import com.ank.japi.json.JsonTemplate;
import com.ank.japi.Response;

@JsonSerialize(using = ResponseImplJsonSerializer.class)
public final class ResponseImpl<T>
        implements Response<T> {

    private final JsonTemplate template;
    private final T            data;
    private final int          statusCode;
    private final String       message;

    public ResponseImpl(
            JsonTemplate template,
            int statusCode, String message, T data
    ) {
        this.template = template;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public ResponseImpl(JsonTemplate template, HttpStatus status, T data) {
        this( template, status.value(), status.getReasonPhrase(), data );
    }

    public ResponseImpl(
            JsonTemplate template, HttpStatus status, String message
    ) {
        this( template, status.value(), message, null );
    }

    Object toJson() {
        if ( template.isConfigured() ) {
            JsonObject json = template.createJsonObject();
            json.set( JsonTemplateImpl.STATUS, statusCode );
            json.set( JsonTemplateImpl.MESSAGE, message );
            json.set( JsonTemplateImpl.DATA, data );
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
