package com.cqx.jdbc.rpc.client;

import com.cqx.jdbc.rpc.JdbcRpcException;
import com.cqx.jdbc.rpc.connection.ConnectionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Driver;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单例
 */
public class RequestFactories {
    private static final Logger log = LoggerFactory.getLogger(RequestFactories.class);
    private final Map<ConnectionInfo, IRequestFactory> requestFactoryMap = new ConcurrentHashMap<>();

    public static RequestFactories getInstance() {
        return LazyHolder.INSTANCE;
    }

    public IRequestFactory addFactory(ConnectionInfo connectionInfo) {
        String requestFactoryClazz = connectionInfo.requestFactoryClass;
        try {
            IRequestFactory instance = (IRequestFactory) Class.forName(requestFactoryClazz).newInstance();
            instance.setConnectionInfo(connectionInfo);
            requestFactoryMap.put(connectionInfo, instance);
            return instance;
        } catch (InstantiationException e) {
            log.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        throw new JdbcRpcException("构建requestFactory失败");
    }

    public IRequestFactory get(ConnectionInfo connectionInfo) {
        return requestFactoryMap.get(connectionInfo);
    }

    private RequestFactories() {
    }

    private static class LazyHolder { private static final RequestFactories INSTANCE = new RequestFactories(); }

}
