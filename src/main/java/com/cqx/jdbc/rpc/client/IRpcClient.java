package com.cqx.jdbc.rpc.client;

import com.cqx.jdbc.rpc.client.DefRequest;
import com.cqx.jdbc.rpc.client.DefResponse;

import java.sql.ResultSet;

/**
 * 真正与目标建立连接的对象
 */
public interface IRpcClient {

    /**
     * 发送sql请求
     * @return
     */
    IResponse sendRequestInterval(IRequest request);

    /**
     * 发送请求
     * @return
     */
    ResultSet sendRequest(String sql);

    /**
     * 连接是否关闭
     * @return
     */
    boolean isClosed();

    /**
     * 关闭连接
     */
    void disconnect();

}
