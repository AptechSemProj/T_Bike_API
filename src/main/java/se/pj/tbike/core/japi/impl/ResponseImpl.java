package se.pj.tbike.core.japi.impl;

import com.ank.japi.json.JsonObject;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.http.HttpStatus;
import com.ank.japi.json.JsonTemplate;
import com.ank.japi.Response;

@JsonSerialize(using = ResponseImplJsonSerializer.class)
public final class ResponseImpl<T>
        implements Response<T> {

    public static final JsonTemplate JSON_TEMPLATE;

    static {
        JSON_TEMPLATE = JsonTemplateImpl.INSTANCE;
    }

    private final T data;
    private final int statusCode;
    private final String message;

    public ResponseImpl(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public ResponseImpl(HttpStatus status, String message) {
        this(status.value(), message, null);
    }

    Object toJson() {
        if (JSON_TEMPLATE.isConfigured()) {
            JsonObject json = JSON_TEMPLATE.createJsonObject();
            json.set(JsonTemplateImpl.STATUS, statusCode);
            json.set(JsonTemplateImpl.MESSAGE, message);
            json.set(JsonTemplateImpl.DATA, data);
            return json.get();
        } else {
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
