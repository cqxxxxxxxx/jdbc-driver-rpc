package com.cqx.jdbc.rpc.client;

import com.cqx.jdbc.rpc.connection.ConnectionInfo;

/**
 * 工厂方法
 */
public class DefRequestFactory implements IRequestFactory {
    private ConnectionInfo connectionInfo;

    @Override
    public void setConnectionInfo(ConnectionInfo connectionInfo) {
        this.connectionInfo = connectionInfo;
    }

    public DefRequest build(String sql) {
        return DefRequest.Builder.builder()
                .withDb(connectionInfo.db)
                .withSql(sql)
                .withHeaders(connectionInfo.httpHeaders)
                .withMethod(connectionInfo.httpMethod)
                .build();
    }


}
