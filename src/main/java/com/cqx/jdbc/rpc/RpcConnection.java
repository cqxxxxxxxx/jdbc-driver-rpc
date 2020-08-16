package com.cqx.jdbc.rpc;

import com.cqx.jdbc.rpc.client.IRpcClient;

import java.sql.Connection;

public interface RpcConnection extends Connection {
    IRpcClient getRpcClient();
}
