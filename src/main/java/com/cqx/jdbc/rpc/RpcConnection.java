package com.cqx.jdbc.rpc;

import com.cqx.jdbc.rpc.client.RpcClient;

import java.sql.Connection;

public interface RpcConnection extends Connection {
    RpcClient getRpcClient();
}
