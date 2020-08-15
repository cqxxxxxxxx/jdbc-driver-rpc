package com.cqx.jdbc.rpc.client;

import java.util.Map;
import java.util.Objects;

public class DefRequest implements IRequest {
    //todo 请求头
    private Map<String, String> header;

    private String sql;

    private String db;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
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
        DefRequest that = (DefRequest) o;
        return Objects.equals(sql, that.sql) &&
                Objects.equals(db, that.db);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sql, db);
    }
}
