package com.ank.japi;

public interface Response<T> {

    T getResponseBody();

    int getStatusCode();

}
