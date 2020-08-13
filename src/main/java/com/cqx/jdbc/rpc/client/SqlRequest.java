package com.cqx.jdbc.rpc.client;

import java.util.Objects;

public class SqlRequest {

    private String sql;

    private String token;

    private String db;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SqlRequest that = (SqlRequest) o;
        return Objects.equals(sql, that.sql) &&
                Objects.equals(token, that.token) &&
                Objects.equals(db, that.db);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sql, token, db);
    }
}
