package com.cqx.jdbc.rpc.client;

import com.cqx.jdbc.rpc.result.Rows;

public interface IResponse {

    /**
     * response转换成rows
     * @return
     */
    Rows toRows();
}
