package com.cqx.jdbc.rpc.client;

import com.cqx.jdbc.rpc.JdbcRpcException;

public interface IRpcSerializer {

    /**
     * Serialize the given object to binary data.
     *
     * @param request to serialize
     * @return the equivalent binary data
     */
    byte[] serialize(IRequest request) throws JdbcRpcException;

    /**
     * Deserialize an object from the given binary data.
     *
     * @param bytes object binary representation
     * @return the equivalent object instance
     */
    <T extends IResponse> T deserialize(byte[] bytes, Class<T> tClass) throws JdbcRpcException;
}
