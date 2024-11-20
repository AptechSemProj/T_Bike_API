package se.pj.tbike.core.japi.impl;

import com.ank.japi.json.JsonObject;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.http.HttpStatus;
import com.ank.japi.json.JsonTemplate;
import com.ank.japi.Response;

import java.util.HashMap;
import java.util.Map;

@JsonSerialize(using = ResponseImplJsonSerializer.class)
public class ResponseImpl<T>
        implements Response<T> {

    public static final class Builder<T> {
        private final int statusCode;
        private String message;
        private T data;

        private Builder(int statusCode, String message) {
            this.statusCode = statusCode;
            this.message = message;
        }

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ResponseImpl<T> build() {
            return new ResponseImpl<>(statusCode, message, data);
        }
    }

    public static final JsonTemplate JSON_TEMPLATE;

    static {
        JSON_TEMPLATE = JsonTemplateImpl.INSTANCE;
    }

    private final T data;
    private final int statusCode;
    private final String message;
    private Map<String, Object> extra;

    private ResponseImpl(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public static <T> Builder<T> status(int statusCode) {
        return new Builder<>(statusCode, null);
    }

    public static <T> Builder<T> status(HttpStatus status) {
        return new Builder<>(status.value(), status.getReasonPhrase());
    }

    public ResponseImpl<T> addExtraField(String key, Object value) {
        if (extra == null) {
            extra = new HashMap<>();
        }
        extra.put(key, value);
        return this;
    }

    Object toJson() {
        if (JSON_TEMPLATE.isConfigured()) {
            JsonObject json = JSON_TEMPLATE.createJsonObject();
            json.set(JsonTemplateImpl.STATUS, statusCode);
            json.set(JsonTemplateImpl.MESSAGE, message);
            json.set(JsonTemplateImpl.DATA, data);
            if (extra != null) {
                for (Map.Entry<String, Object> entry : extra.entrySet()) {
                    json.set(entry.getKey(), entry.getValue());
                }
            }
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
