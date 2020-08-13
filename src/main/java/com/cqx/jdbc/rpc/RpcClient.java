package com.cqx.jdbc.rpc;

import com.cqx.jdbc.rpc.client.SqlRequest;
import com.cqx.jdbc.rpc.client.SqlResponse;

public interface RpcClient {
    /**
     * 发送sql请求
     * @return
     */
    SqlResponse sendRequest(SqlRequest sqlRequest);

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
