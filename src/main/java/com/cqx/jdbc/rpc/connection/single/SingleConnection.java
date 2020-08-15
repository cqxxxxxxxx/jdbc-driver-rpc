package com.cqx.jdbc.rpc.connection.single;

import com.cqx.jdbc.rpc.client.RpcClient;
import com.cqx.jdbc.rpc.connection.ConnectionImpl;
import com.cqx.jdbc.rpc.connection.ConnectionInfo;

public class SingleConnection extends ConnectionImpl {

    public SingleConnection(ConnectionInfo connectionInfo, RpcClient rpcClient) {
        super(connectionInfo, rpcClient);
    }
}
