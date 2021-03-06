package com.cqx.jdbc.rpc;


import com.cqx.jdbc.rpc.client.IRpcClient;
import com.cqx.jdbc.rpc.client.RpcClients;
import com.cqx.jdbc.rpc.client.http.HttpUrlConnectionClient;
import com.cqx.jdbc.rpc.connection.ConnectionInfo;

import java.sql.SQLException;

public class HttpDriver extends RpcDriver {
    private static final String PROTOCOL = "jdbc:rpc:http://";
    //
    // Register ourselves with the DriverManager
    //
    static {
        try {
            java.sql.DriverManager.registerDriver(new HttpDriver());
        } catch (SQLException E) {
            throw new RuntimeException("Can't register driver!");
        }
    }

    /**
     * Construct a new driver and register it with DriverManager
     *
     * @throws SQLException
     *             if a database error occurs.
     */
    public HttpDriver() throws SQLException {
        // Required for Class.forName().newInstance()
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        return url != null && url.startsWith(PROTOCOL);
    }
}
