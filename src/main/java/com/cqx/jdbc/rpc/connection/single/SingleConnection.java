package com.cqx.jdbc.rpc.connection.single;

import com.cqx.jdbc.rpc.RpcClient;
import com.cqx.jdbc.rpc.connection.ConnectionImpl;
import com.cqx.jdbc.rpc.connection.ConnectionInfo;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class SingleConnection extends ConnectionImpl {

    public SingleConnection(ConnectionInfo connectionInfo, RpcClient rpcClient) {
        super(connectionInfo, rpcClient);
    }
}
