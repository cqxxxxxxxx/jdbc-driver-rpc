package com.cqx.jdbc.rpc.client;

import com.cqx.jdbc.rpc.JdbcRpcException;
import com.cqx.jdbc.rpc.util.JSONUtil;
import com.fasterxml.jackson.core.type.TypeReference;

import java.nio.charset.StandardCharsets;

public class DefJacksonSerializer implements IRpcSerializer {

    /**
     * Serialize the given object to binary data.
     *
     * @param request to serialize
     * @return the equivalent binary data
     */
    @Override
    public byte[] serialize(IRequest request) throws JdbcRpcException {
        return JSONUtil.toJson(request).getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Deserialize an object from the given binary data.
     *
     * @param bytes object binary representation
     * @return the equivalent object instance
     */
    @Override
    public <T extends IResponse> T deserialize(byte[] bytes, Class<T> tClass) throws JdbcRpcException {
        String json = new String(bytes, StandardCharsets.UTF_8);
        return JSONUtil.toObject(json, tClass);
    }


}
