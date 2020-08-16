package com.cqx.jdbc.rpc.client;

import com.cqx.jdbc.rpc.JdbcRpcException;
import com.cqx.jdbc.rpc.connection.ConnectionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * rpcClient的拦截器管理类
 * 维护connectionInfo与对应的拦截器的关联
 */
public class RpcClientInterceptors {
    private static final Logger log = LoggerFactory.getLogger(RpcClientInterceptors.class);
    private final Map<ConnectionInfo, List<IRpcClientInterceptor>> rpcClientInterceptorMap = new ConcurrentHashMap<>();

    public static RpcClientInterceptors getInstance() {
        return RpcClientInterceptors.LazyHolder.INSTANCE;
    }

    public void addInterceptors(ConnectionInfo connectionInfo) {
        final List<String> rpcClientInterceptorClasses = connectionInfo.rpcClientInterceptorClasses;
        try {
            List<IRpcClientInterceptor> interceptors = new ArrayList<>();
            for (String interceptorClass : rpcClientInterceptorClasses) {
                final IRpcClientInterceptor interceptor = (IRpcClientInterceptor) Class.forName(interceptorClass).newInstance();
                interceptors.add(interceptor);
            }
            rpcClientInterceptorMap.put(connectionInfo, interceptors);
        } catch (InstantiationException e) {
            log.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        throw new JdbcRpcException("构建rpcInterceptors失败");
    }

    public List<IRpcClientInterceptor> get(ConnectionInfo connectionInfo) {
        return rpcClientInterceptorMap.get(connectionInfo);
    }

    private RpcClientInterceptors() {
    }

    private static class LazyHolder { private static final RpcClientInterceptors INSTANCE = new RpcClientInterceptors(); }

}
