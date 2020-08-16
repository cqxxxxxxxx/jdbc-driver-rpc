package com.cqx.jdbc.rpc.client;

import com.cqx.jdbc.rpc.JdbcRpcException;
import com.cqx.jdbc.rpc.connection.ConnectionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RpcSerializers {
    private static final Logger log = LoggerFactory.getLogger(RpcSerializers.class);
    private final Map<ConnectionInfo, IRpcSerializer> rpcSerializerMap = new ConcurrentHashMap<>();

    public static RpcSerializers getInstance() {
        return LazyHolder.INSTANCE;
    }

    public IRpcSerializer addSerializer(ConnectionInfo connectionInfo) {
        String rpcSerializerClazz = connectionInfo.rpcSerializerClass;
        try {
            IRpcSerializer instance = (IRpcSerializer) Class.forName(rpcSerializerClazz).newInstance();
            rpcSerializerMap.put(connectionInfo, instance);
            return instance;
        } catch (InstantiationException e) {
            log.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        throw new JdbcRpcException("构建rpcSerializer失败");
    }

    public IRpcSerializer get(ConnectionInfo connectionInfo) {
        return rpcSerializerMap.get(connectionInfo);
    }

    private RpcSerializers() {
    }

    /**
     * 基于jvm类加载机制实现的懒加载
     */
    private static class LazyHolder { private static final RpcSerializers INSTANCE = new RpcSerializers(); }

}
