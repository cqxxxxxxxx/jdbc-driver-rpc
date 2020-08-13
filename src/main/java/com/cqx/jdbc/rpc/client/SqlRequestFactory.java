package com.cqx.jdbc.rpc.client;

import com.cqx.jdbc.rpc.connection.ConnectionInfo;

/**
 * 工厂方法
 */
public class SqlRequestFactory {
    private ConnectionInfo connectionInfo;

    public SqlRequestFactory(ConnectionInfo connectionInfo) {
        this.connectionInfo = connectionInfo;
    }

    public SqlRequest build(String sql) {
        SqlRequest sqlRequest = new SqlRequest();
        sqlRequest.setSql(sql);
        sqlRequest.setDb(connectionInfo.db);
        sqlRequest.setToken(connectionInfo.token);
        return sqlRequest;
    }

}
