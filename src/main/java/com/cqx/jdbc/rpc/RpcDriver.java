package com.cqx.jdbc.rpc;

import com.cqx.jdbc.rpc.aware.Awares;
import com.cqx.jdbc.rpc.aware.BeforeBuildAware;
import com.cqx.jdbc.rpc.client.RequestFactories;
import com.cqx.jdbc.rpc.client.RpcClient;
import com.cqx.jdbc.rpc.client.RpcSerializers;
import com.cqx.jdbc.rpc.connection.ConnectionInfo;
import com.cqx.jdbc.rpc.connection.single.SingleConnection;

import java.sql.*;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * driver基类。 模板方法模式，实现了需要的
 */
public abstract class RpcDriver implements Driver {

    public RpcDriver() {
//        Awares.awareBeforeBuild();
    }

    /**
     * 子类基于不同的rpc方案实现不同的rpcclient
     *
     * @param connectionInfo
     * @return
     */
    protected abstract RpcClient buildRpcClient(ConnectionInfo connectionInfo);

    /**
     * 尝试使用提供的url获取一个数据库连接
     * 驱动应该返回null，如果url跟driver的类型不匹配。这个是正常现象，当driver manager通过提供的url来获取连接时，
     * 他会依次调用加载的所有driver的connect方法。
     *
     * Attempts to make a database connection to the given URL.
     * The driver should return "null" if it realizes it is the wrong kind
     * of driver to connect to the given URL.  This will be common, as when
     * the JDBC driver manager is asked to connect to a given URL it passes
     * the URL to each loaded driver in turn.
     *
     * <P>The driver should throw an <code>SQLException</code> if it is the right
     * driver to connect to the given URL but has trouble connecting to
     * the database.
     *
     * <P>The {@code Properties} argument can be used to pass
     * arbitrary string tag/value pairs as connection arguments.
     * Normally at least "user" and "password" properties should be
     * included in the {@code Properties} object.
     * <p>
     * <B>Note:</B> If a property is specified as part of the {@code url} and
     * is also specified in the {@code Properties} object, it is
     * implementation-defined as to which value will take precedence. For
     * maximum portability, an application should only specify a property once.
     *
     * @param url the URL of the database to which to connect
     * @param info a list of arbitrary string tag/value pairs as
     * connection arguments. Normally at least a "user" and
     * "password" property should be included.
     * @return a <code>Connection</code> object that represents a
     *         connection to the URL
     * @exception SQLException if a database access error occurs or the url is
     * {@code null}
     */
    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        /**
         * 0. 校验是否支持该url
         */
        if (!ConnectionInfo.acceptsUrl(url)) {
            /*
             * According to JDBC spec:
             * The driver should return "null" if it realizes it is the wrong kind of driver to connect to the given URL. This will be common, as when the
             * JDBC driver manager is asked to connect to a given URL it passes the URL to each loaded driver in turn.
             */
            return null;
        }
        /**
         * 1.构建连接信息（url，db，连接类型...）
         */
        ConnectionInfo conInfo = ConnectionInfo.getConnectionUrlInstance(url, info);

        /**
         * 2.添加对应的请求构造工厂类
         */
        RequestFactories.getInstance().addFactory(conInfo);

        /**
         * 3.添加对应的Rpc序列化器
         */
        RpcSerializers.getInstance().addSerializer(conInfo);

        /**
         * 4.创建rpcClient
         * - 建立底层连接
         * - 基于connectionInfo中用户密码信息，请求获取并设置token
         */
        RpcClient rpcClient = buildRpcClient(conInfo);

        /**
         * 5.创建连接
         */
        switch (conInfo.type) {
            case LOADBALANCE_CONNECTION:
            case SINGLE_CONNECTION:
                return new SingleConnection(conInfo, rpcClient);
            default:
        }
        return null;
    }


    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return new DriverPropertyInfo[0];
    }

    @Override
    public int getMajorVersion() {
        return Constants.MAJOR_VERSION;
    }

    @Override
    public int getMinorVersion() {
        return Constants.MINOR_VERSION;
    }

    /**
     * 反正这个是肯定不合格的，返回false就完事了
     *
     * 表示这个驱动是否是真正合规的jdbc驱动
     *
     * Reports whether this driver is a genuine JDBC
     * Compliant&trade; driver.
     *
     * 驱动只有通过了jdbc合规性测试才能返回true，否则false
     * A driver may only report <code>true</code> here if it passes the JDBC
     * compliance tests; otherwise it is required to return <code>false</code>.
     * <P>
     *
     * jdbc合规性测试需要支持全部的jdbc api，并且支持全部的sql 92 entry level。
     * JDBC compliance requires full support for the JDBC API and full support
     * for SQL 92 Entry Level.  It is expected that JDBC compliant drivers will
     * be available for all the major commercial databases.
     * <P>
     * This method is not intended to encourage the development of non-JDBC
     * compliant drivers, but is a recognition of the fact that some vendors
     * are interested in using the JDBC API and framework for lightweight
     * databases that do not support full database functionality, or for
     * special databases such as document information retrieval where a SQL
     * implementation may not be feasible.
     * @return <code>true</code> if this driver is JDBC Compliant; <code>false</code>
     *         otherwise
     */
    @Override
    public boolean jdbcCompliant() {
        return false;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException();
    }
}
