package com.ank.japi;

@Deprecated
public interface ResponseBuilder {

//    ResponseBuilder headers(HttpHeaders headers);
//    ResponseBuilder headers(Consumer<HttpHeaders> headers);

    /* 2xx */

    Response ok(Object data, String message);

    default Response ok(Object data) {
        return ok(data, "Ok");
    }

    default Response ok() {
        return ok(null);
    }

    Response created(Object data, String message);

    default Response created(Object data) {
        return created(data, "Created");
    }

    default Response created() {
        return created(null);
    }

    Response noContent(String message);

    default Response noContent() {
        return noContent("No Content");
    }
}
