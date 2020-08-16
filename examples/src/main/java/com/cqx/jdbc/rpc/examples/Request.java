package com.cqx.jdbc.rpc.examples;

public class Request {
    private String db;
    private String sql;

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
