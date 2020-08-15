package com.cqx.jdbc.rpc.client.http;

import com.cqx.jdbc.rpc.Constants;
import com.cqx.jdbc.rpc.JdbcRpcException;
import com.cqx.jdbc.rpc.client.*;
import com.cqx.jdbc.rpc.connection.ConnectionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * 基于HttpURLConnection实现的http服务调用
 */
public class HttpUrlConnectionClient extends AbstractRpcClient {
    private static Logger logger = LoggerFactory.getLogger(HttpUrlConnectionClient.class);

    /**
     * 底层tcp连接观察者，用于关闭连接，观察连接是否活跃之类
     */
    private HttpURLConnection connectionWatcher;

    public HttpUrlConnectionClient(ConnectionInfo connectionInfo) {
        super(connectionInfo);
    }

    /**
     * 提前建立底层与目标服务的连接
     *
     * @param connectionInfo
     * @return
     */
    @Override
    protected boolean preBuildConn(ConnectionInfo connectionInfo) {
        try {
            URL sqlUrl = new URL(connectionInfo.url);
            connectionWatcher = (HttpURLConnection) sqlUrl.openConnection();
            int responseCode = connectionWatcher.getResponseCode();
            return responseCode == 200;
        } catch (MalformedURLException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }


    /**
     * 实际发送请求的部分逻辑
     *
     * @param content
     * @return
     */
    @Override
    protected byte[] doSendRequest(byte[] content) {
        try {
            URL url = new URL(connectionInfo.url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            //todo 如何合理优雅的设置请求头认证
            con.setRequestProperty("Authentication", connectionInfo.authenticationValue);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            try (OutputStream os = con.getOutputStream()) {
                os.write(content, 0, content.length);
            }
            try (InputStream input = con.getInputStream()) {
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int n = 0;
                while (-1 != (n = input.read(buffer))) {
                    output.write(buffer, 0, n);
                }
                return output.toByteArray();
            }
        } catch (MalformedURLException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        throw new JdbcRpcException("请求异常");
    }

    /**
     * 关闭连接
     */
    @Override
    public void disconnect() {
        connectionWatcher.disconnect();
        super.disconnect();
    }

}
