package com.cqx.jdbc.rpc.client;

import com.cqx.jdbc.rpc.JdbcRpcException;
import com.cqx.jdbc.rpc.connection.ConnectionInfo;
import com.cqx.jdbc.rpc.result.ResultSetImpl;
import com.cqx.jdbc.rpc.result.Rows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.util.List;

public abstract class AbstractRpcClient implements IRpcClient {
    private static Logger logger = LoggerFactory.getLogger(AbstractRpcClient.class);
    protected boolean isClosed = true;
    protected ConnectionInfo connectionInfo;
    protected IRequestFactory requestFactory;
    protected IRpcSerializer rpcSerializer;
    protected List<IRpcClientInterceptor> interceptors;

    /**
     * 提前建立底层与目标服务的连接
     * @param connectionInfo
     * @return
     */
    protected abstract boolean preBuildConn(ConnectionInfo connectionInfo);

    public AbstractRpcClient(ConnectionInfo connectionInfo) {
        try {
            if ((isClosed = !preBuildConn(connectionInfo))) {
                throw new JdbcRpcException("连接建立失败");
            }
            this.connectionInfo = connectionInfo;
            this.requestFactory = RequestFactories.getInstance().get(connectionInfo);
            this.rpcSerializer = RpcSerializers.getInstance().get(connectionInfo);
            this.interceptors = RpcClientInterceptors.getInstance().get(connectionInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

    /**
     * 发送请求
     * 解析返回response对象
     * 实际发送请求的部分逻辑
     *
     * @return
     */
    @Override
    public abstract IResponse sendRequestInterval(IRequest request);

    /**
     * 发送请求
     * 解析成rows并返回resultSet
     *
     * @param sql
     * @return
     */
    @Override
    public ResultSet sendRequest(String sql) {
        IResponse response = null;
        try {
            IRequest request = requestFactory.build(sql);
            interceptors.forEach(x -> x.beforeSend(request));
            response = this.sendRequestInterval(request);
            Rows rows = response.toRows();
            return new ResultSetImpl(rows, this);
        } catch (Exception ex) {
            final IResponse finalResponse = response;
            interceptors.forEach(x -> x.onError(finalResponse, ex));
            throw ex;
        }
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
