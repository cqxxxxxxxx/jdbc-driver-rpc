package com.cqx.jdbc.rpc;

public class JdbcRpcException extends RuntimeException {
    public JdbcRpcException() {
        super();
    }

    public JdbcRpcException(String msg) {
        super(msg);
    }


    public JdbcRpcException(String msg, Exception e) {
        super(msg, e);
    }
}
