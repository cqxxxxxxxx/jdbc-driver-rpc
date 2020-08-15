package com.cqx.jdbc.rpc.client;

import com.cqx.jdbc.rpc.connection.ConnectionInfo;

public interface IRequestFactory {
    void setConnectionInfo(ConnectionInfo connectionInfo);

    IRequest build(String sql);
}
