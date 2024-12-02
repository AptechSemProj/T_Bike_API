package se.pj.tbike.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Response<T> extends com.ank.japi.Response {

    public static final class Builder {
        private final int statusCode;
        private String message;

        private Builder(int statusCode, String message) {
            this.statusCode = statusCode;
            this.message = message;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Response<?> ofThrowable(Throwable throwable) {
            return new Response<>(
                    statusCode,
                    throwable.getLocalizedMessage(),
                    null
            );
        }

        public <T> Response<T> build(T data) {
            return new Response<>(statusCode, message, data);
        }

        public <T> Response<T> build() {
            return new Response<>(statusCode, message, null);
        }
    }

    private static final String STATUS = "status";
    private static final String MESSAGE = "message";
    private static final String DATA = "data";
    private static final String METADATA = "metadata";

    private final Map<String, Object> json;

    private Response(int statusCode, String message, T data) {
        json = new HashMap<>();
        json.put(STATUS, statusCode);
        json.put(MESSAGE, Objects.requireNonNull(message));
        if (data != null) {
            json.put(DATA, data);
        }
    }

    public static Builder status(int statusCode) {
        return new Builder(statusCode, null);
    }

    public static Builder status(HttpStatus status) {
        return new Builder(status.value(), status.getReasonPhrase());
    }

    public Response<T> metadata(long totalElements,
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

    @Override
    protected void write(JsonGenerator gen) throws IOException {
        gen.writeObject(json);
    }
}
