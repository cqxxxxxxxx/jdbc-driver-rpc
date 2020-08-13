package com.cqx.jdbc.rpc.client;

import com.cqx.jdbc.rpc.RpcClient;
import com.cqx.jdbc.rpc.connection.ConnectionInfo;
import com.cqx.jdbc.rpc.util.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 基于HttpURLConnection实现的http服务调用
 */
public class HttpUrlConnectionClient implements RpcClient {
    private static Logger logger = LoggerFactory.getLogger(HttpUrlConnectionClient.class);
    private boolean isClosed = true;

    private ConnectionInfo connectionInfo;

    /**
     * 底层tcp连接观察者，用于关闭连接，观察连接是否活跃之类
     */
    private HttpURLConnection connectionWatcher;

    public HttpUrlConnectionClient(ConnectionInfo connectionInfo) {
        try {
            this.connectionInfo = connectionInfo;
            URL sqlUrl = new URL(connectionInfo.url);
            connectionWatcher = (HttpURLConnection)sqlUrl.openConnection();
            int responseCode = connectionWatcher.getResponseCode();
            isClosed = !(responseCode == 200);
        } catch (MalformedURLException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        if (isClosed) {
            throw new IllegalStateException("连接建立失败");
        }
    }

    /**
     * 发送sql请求
     *
     * @return
     */
    @Override
    public SqlResponse sendRequest(SqlRequest sqlRequest) {
        String json = post(sqlRequest);
        return JSONUtil.toObject(json, SqlResponse.class);
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
        connectionWatcher.disconnect();
        this.isClosed = true;
    }

    private String post(SqlRequest sqlRequest) {
        try {
            URL url = new URL(connectionInfo.url);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
//            con.setRequestMethod("POST");
//            con.setRequestProperty("Content-Type", "application/json; utf-8");
//            con.setRequestProperty("Accept", "application/json");
//            con.setDoOutput(true);
//            String json = JSONUtil.toJson(sqlRequest);
//            try(OutputStream os = con.getOutputStream()) {
//                byte[] input = json.getBytes("utf-8");
//                os.write(input, 0, input.length);
//            }
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            }
        } catch (MalformedURLException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return "error";
    }
}
