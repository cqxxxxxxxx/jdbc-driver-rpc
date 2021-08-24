package com.cqx.jdbc.rpc;

import java.sql.SQLException;

/**
 * @Deprecate 没用，只需要httpDriver就好了
 */
public class Driver extends com.cqx.jdbc.rpc.HttpDriver {

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
    public Driver() throws SQLException {
        // Required for Class.forName().newInstance()
    }
}
