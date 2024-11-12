package com.ank.japi;

public interface RequestConfigurer<T extends Request<?>> {

    QueryParamsWriter getQueryParamsWriter();

    T getRequestBody();

}
