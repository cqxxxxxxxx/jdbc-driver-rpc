package com.cqx.jdbc.rpc.connection;


import com.cqx.jdbc.rpc.connection.single.SingleConnectionInfo;

import java.util.*;

/**
 * 维护连接信息
 *
 */
public abstract class ConnectionInfo {
    public Type type;
    public RpcClientType rpcClientType;
    public String originalConnStr;
    public Map<String, String> properties;
    public String url;
    public String db;
    public String token;

    public ConnectionInfo(String url, Properties properties) {
        this.originalConnStr = url;
        this.url = url.substring(5);
        this.properties = new HashMap<>();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            String key = entry.getKey().toString();
            this.properties.put(key, properties.getProperty(key, "default value"));
        }
    }

    public static ConnectionInfo getConnectionUrlInstance(String url, Properties info) {
        Type figuredType = Type.figure(url);
        switch (figuredType) {
            case SINGLE_CONNECTION:
                return new SingleConnectionInfo(url, info);
            case LOADBALANCE_CONNECTION:
                //todo
            default:
        }
        //should not happen
        return null;
    }

    /**
     * Checks if this {@link ConnectionInfo} is able to process the given database URL.
     * @param url
     * @return
     */
    public static boolean acceptsUrl(String url) {
        return true;
    }

    /**
     * 连接类型
     * 暂时只实现SINGLE_CONNECTION
     * TODO: lb连接
     */
    public enum Type {
        SINGLE_CONNECTION("jdbc:http:"),
        LOADBALANCE_CONNECTION("jdbc:http:lb");
        private String schema;

        Type(String schema) {
            this.schema = schema;
        }

        public static Type figure(String schema) {
            for (Type value : Type.values()) {
                String pattern = value.schema;
                if (schema.startsWith(pattern)) {
                    return value;
                }
            }
            //should not happen
            return null;
        }
    }

    public enum RpcClientType {
        HttpUrlConnectionClient
    }
}
