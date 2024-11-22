package se.pj.tbike.core.japi.impl;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.http.HttpStatus;
import com.ank.japi.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    private static final String STATUS = "status";
    private static final String MESSAGE = "message";
    private static final String DATA = "data";
    private static final String METADATA = "metadata";

    private final Map<String, Object> json;

    private ResponseImpl(int statusCode, String message, T data) {
        json = new HashMap<>();
        json.put(STATUS, statusCode);
        json.put(MESSAGE, Objects.requireNonNull(message));
        if (data != null) {
            json.put(DATA, data);
        }
    }

    public static <T> Builder<T> status(int statusCode) {
        return new Builder<>(statusCode, null);
    }

    public static <T> Builder<T> status(HttpStatus status) {
        return new Builder<>(status.value(), status.getReasonPhrase());
    }

    public ResponseImpl<T> metadata(long totalElements,
                                    int totalPages,
                                    int size,
                                    int current,
                                    Integer next,
                                    Integer previous) {
        Map<String, Number> metadata = new HashMap<>() {{
            put("total_elements", totalElements);
            put("total_pages", totalPages);
            put("page_size", size);
            put("current_page", current);
            put("next", next);
            put("previous", previous);
        }};
        json.put(METADATA, metadata);
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getResponseBody() {
        return (T) json.get(DATA);
    }

    @Override
    public int getStatusCode() {
        return (int) json.get(STATUS);
    }

    Object toJson() {
        return json;
    }
}
