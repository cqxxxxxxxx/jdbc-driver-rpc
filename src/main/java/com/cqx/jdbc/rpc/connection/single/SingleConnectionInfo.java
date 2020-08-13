package com.cqx.jdbc.rpc.connection.single;

import com.cqx.jdbc.rpc.connection.ConnectionInfo;

import java.util.Properties;

public class SingleConnectionInfo extends ConnectionInfo {

    public SingleConnectionInfo(String url, Properties properties) {
        super(url, properties);
        this.type = Type.SINGLE_CONNECTION;
        //todo 根据配置解析
        this.rpcClientType = RpcClientType.HttpUrlConnectionClient;
    }
}
