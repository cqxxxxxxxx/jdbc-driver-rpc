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
        DefRequest defRequest = new DefRequest();
        defRequest.setSql(sql);
        defRequest.setDb(connectionInfo.db);
        //todo 请求头
        return defRequest;
    }


}
