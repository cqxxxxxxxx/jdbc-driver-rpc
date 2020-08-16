package com.cqx.jdbc.rpc.client;

public interface IRpcClientInterceptor {
    void name();

    default void beforeSend(IRequest request) {};

    default void afterSend(IResponse response) {};

    default void onError(IResponse response, Exception e) {};
}
