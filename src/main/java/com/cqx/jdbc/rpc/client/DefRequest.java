package com.cqx.jdbc.rpc.client;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Map;
import java.util.Objects;

public class DefRequest implements IRequest {

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


    public static final class Builder {
        private String sql;
        private String db;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder withSql(String sql) {
            this.sql = sql;
            return this;
        }

        public Builder withDb(String db) {
            this.db = db;
            return this;
        }

        public DefRequest build() {
            DefRequest defRequest = new DefRequest();
            defRequest.setSql(sql);
            defRequest.setDb(db);
            return defRequest;
        }
    }
}
