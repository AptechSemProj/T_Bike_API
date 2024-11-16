package com.ank.japi;

/**
 * @param <B> response body type
 */
public interface Response<B> {

    B getResponseBody();

    int getStatusCode();

}
