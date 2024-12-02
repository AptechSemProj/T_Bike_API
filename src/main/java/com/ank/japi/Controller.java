package com.ank.japi;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public class Controller {

    protected final ResponseConfigurer configurer;

    protected Controller(ResponseConfigurer configurer) {
        this.configurer = configurer;
    }

    protected Controller() {
        this(new ResponseConfigurer());
    }

    protected Response tryCatch(Callable callable) {
        try {
            return callable.call();
        } catch (Throwable throwable) {
            return configurer.createResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    throwable
            );
        }
    }

// 2xx

    protected Response ok() {
        return ok(null);
    }

    protected Response ok(Object body) {
        return configurer.createResponse(OK, body);
    }

    protected Response ok(Object body, HttpHeaders headers) {
        return configurer.createResponse(OK, body)
                .headers(headers);
    }

    protected Response ok(Object body, Consumer<HttpHeaders> headers) {
        return configurer.createResponse(OK, body)
                .headers(headers);
    }

    protected Response created() {
        return created(null);
    }

    protected Response created(Object body) {
        return configurer.createResponse(CREATED, body);
    }

    protected Response created(Object body, HttpHeaders headers) {
        return configurer.createResponse(CREATED, body)
                .headers(headers);
    }

    protected Response created(Object body, Consumer<HttpHeaders> headers) {
        return configurer.createResponse(CREATED, body)
                .headers(headers);
    }

//    protected void accepted() {
//    }

    protected Response noContent() {
        return configurer.createResponse(NO_CONTENT, null);
    }

    protected Response noContent(HttpHeaders headers) {
        return configurer.createResponse(NO_CONTENT, null)
                .headers(headers);
    }

    protected Response noContent(Consumer<HttpHeaders> headers) {
        return configurer.createResponse(NO_CONTENT, null)
                .headers(headers);
    }

//    protected void badRequest() {
//    }
//
//    protected void unauthorized() {
//    }
//
//    protected void forbidden() {
//    }
//
//    protected void notFound() {
//    }
//
//    protected void conflict() {
//    }
//
//    protected void internalServerError() {
//    }
//
//    protected void notImplemented() {
//    }

    @FunctionalInterface
    protected interface Callable {
        Response call() throws Throwable;
    }

    @FunctionalInterface
    public interface ResponseBuilder {
        Response build(int statusCode, Object body);
    }

    public static class ResponseConfigurer {

        private final Map<HttpStatus, Integer> statuses = new HashMap<>();
        private final List<Binding<?>> errors;
        private ResponseBuilder responseBuilder;

        public ResponseConfigurer() {
            errors = new ArrayList<>();
            responseBuilder = (statusCode, body) -> Response.create(body);
        }

        public Response createResponse(HttpStatus status, Object body) {
            int statusCode = statuses.getOrDefault(status, status.value());
            if (body instanceof Throwable throwable) {
                for (Binding<?> binding : errors) {
                    if (binding.matches(throwable)) {
                        return binding.apply(statusCode, throwable);
                    }
                }
                throw new RuntimeException(throwable);
            }
            return responseBuilder.build(statusCode, body);
        }

        public ResponseConfigurer setStatusCode(HttpStatus status, int newCode) {
            statuses.put(status, newCode);
            return this;
        }

        public ResponseConfigurer setResponseBuilder(ResponseBuilder builder) {
            this.responseBuilder = builder;
            return this;
        }

        public ResponseConfigurer setErrorBinding(Binding<?> binding) {
            if (binding != null) {
                errors.add(binding);
            }
            return this;
        }

        public static final class Binding<E extends Throwable> {
            private final Class<?> clazz;
            private final ResponseBuilder mapper;

            @SuppressWarnings("unchecked")
            public Binding(
                    Class<E> clazz,
                    Function<E, ? extends Response> mapper
            ) {
                this.clazz = clazz;
                this.mapper = (statusCode, body) -> mapper.apply((E) body);
            }

            public static <E extends Throwable>
            Binding<E> bind(
                    Class<E> clazz,
                    Function<E, ? extends Response> mapper
            ) {
                return new Binding<>(clazz, mapper);
            }

            private boolean matches(Throwable throwable) {
                return clazz.isInstance(throwable);
            }

            private Response apply(int statusCode, Throwable throwable) {
                return mapper.build(statusCode, throwable);
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) {
                    return true;
                }
                if (!(o instanceof Binding<?> that)) {
                    return false;
                }
                return Objects.equals(clazz, that.clazz)
                        && Objects.equals(mapper, that.mapper);
            }

            @Override
            public int hashCode() {
                return Objects.hash(clazz, mapper);
            }
        }
    }
}
