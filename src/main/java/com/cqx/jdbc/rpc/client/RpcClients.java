package com.cqx.jdbc.rpc.client;

import com.cqx.jdbc.rpc.JdbcRpcException;
import com.cqx.jdbc.rpc.connection.ConnectionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RpcClients {
    private static final Logger log = LoggerFactory.getLogger(RpcClients.class);
    private final Map<ConnectionInfo, IRpcClient> rpcSerializerMap = new ConcurrentHashMap<>();

    public static RpcClients getInstance() {
        return RpcClients.LazyHolder.INSTANCE;
    }

    public IRpcClient addRpcClient(ConnectionInfo connectionInfo) {
        String rpcClientClazz = connectionInfo.rpcClientClass;
        try {
            final Class<?> clazz = Class.forName(rpcClientClazz);
            final Constructor<?> constructor = clazz.getConstructor(ConnectionInfo.class);
            final IRpcClient instance = (IRpcClient) constructor.newInstance(connectionInfo);
            rpcSerializerMap.put(connectionInfo, instance);
            return instance;
        } catch (InstantiationException e) {
            log.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
        } catch (NoSuchMethodException e) {
            log.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            log.error(e.getMessage(), e);
        }
        throw new JdbcRpcException("构建rpcSerializer失败");
    }

    public IRpcClient get(ConnectionInfo connectionInfo) {
        return rpcSerializerMap.get(connectionInfo);
    }

    private RpcClients() {
    }

    /**
     * 基于jvm类加载机制实现的懒加载
     */
    private static class LazyHolder { private static final RpcClients INSTANCE = new RpcClients(); }

}
