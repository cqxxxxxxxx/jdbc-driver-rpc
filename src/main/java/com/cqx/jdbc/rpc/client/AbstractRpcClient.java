package com.cqx.jdbc.rpc.client;

import com.cqx.jdbc.rpc.JdbcRpcException;
import com.cqx.jdbc.rpc.connection.ConnectionInfo;
import com.cqx.jdbc.rpc.result.ResultSetImpl;
import com.cqx.jdbc.rpc.result.Rows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;

public abstract class AbstractRpcClient implements RpcClient {
    private static Logger logger = LoggerFactory.getLogger(AbstractRpcClient.class);
    protected boolean isClosed = true;
    protected ConnectionInfo connectionInfo;
    protected IRequestFactory requestFactory;
    protected IRpcSerializer rpcSerializer;

    /**
     * 提前建立底层与目标服务的连接
     * @param connectionInfo
     * @return
     */
    protected abstract boolean preBuildConn(ConnectionInfo connectionInfo);

    /**
     * 实际发送请求的部分逻辑
     * @param content
     * @return
     */
    protected abstract byte[] doSendRequest(byte[] content);

    public AbstractRpcClient(ConnectionInfo connectionInfo) {
        try {
            if ((isClosed = !preBuildConn(connectionInfo))) {
                throw new JdbcRpcException("连接建立失败");
            }
            this.connectionInfo = connectionInfo;
            this.requestFactory = RequestFactories.getInstance().get(connectionInfo);
            this.rpcSerializer = RpcSerializers.getInstance().get(connectionInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

    /**
     * 发送请求
     * 解析返回response对象
     *
     * @return
     */
    @Override
    public IResponse sendRequestInterval(IRequest request) {
        byte[] bytes = rpcSerializer.serialize(request);
        return rpcSerializer.deserialize(doSendRequest(bytes));
    }

    /**
     * 发送请求
     * 解析成rows并返回resultSet
     *
     * @param sql
     * @return
     */
    @Override
    public ResultSet sendRequest(String sql) {
        IRequest request = requestFactory.build(sql);
        IResponse response = this.sendRequestInterval(request);
        Rows rows = response.toRows();
        return new ResultSetImpl(rows, this);
    }

    @Override
    public boolean isClosed() {
        return isClosed;
    }

    /**
     * 关闭连接
     */
    @Override
    public void disconnect() {
        this.isClosed = true;
    }
}
