package com.ank.japi;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.function.Consumer;

@JsonSerialize(using = Response.ResponseSerializer.class)
public class Response {

    private final HttpHeaders headers;
    private Object body;

    protected Response() {
        this.headers = new HttpHeaders();
    }

    public static Response create(Object body) {
        return new Response().body(body);
    }

    public static Response create() {
        return new Response();
    }

    public final Response headers(HttpHeaders headers) {
        if (headers != null) {
            this.headers.putAll(headers);
        }
        return this;
    }

    public final Response headers(Consumer<HttpHeaders> consumer) {
        consumer.accept(this.headers);
        return this;
    }

    public final Response body(Object body) {
        this.body = body;
        return this;
    }

    public ResponseEntity<Object> toResponseEntity() {
        return toResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity<Object> toResponseEntity(HttpStatus status) {
        return new ResponseEntity<>(body, headers, status);
    }

    protected void write(JsonGenerator gen)
            throws IOException {
        gen.writeObject(body);
    }

    static class ResponseSerializer extends StdSerializer<Response> {

        protected ResponseSerializer() {
            super(Response.class);
        }

        @Override
        public void serialize(Response value, JsonGenerator gen,
                              SerializerProvider provider)
                throws IOException {
            value.write(gen);
        }
    }
}
