package com.cqx.jdbc.rpc;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DriverTest {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.cqx.jdbc.rpc.Driver");
        String url = "jdbc:http://localhost:9001/cqx.json";
        String user = "root";
        String password = "123456";
        Connection connection = DriverManager.getConnection(url,user,password);

        String sql = "select * from stn_user where id = 1 ";
        // 创建Statement对象（每一个Statement为一次数据库执行请求）
        Statement stmt = connection.createStatement();

        // 设置传入参数
        System.out.println(stmt.getResultSet());
        // 执行SQL语句
        ResultSet rs = stmt.executeQuery(sql);
        // 处理查询结果（将查询结果转换成List<Map>格式）
        ResultSetMetaData rsmd = rs.getMetaData();
        int num = rsmd.getColumnCount();
        List<Map<String, String>> result = new ArrayList<>();
        while(rs.next()){
            Map map = new HashMap();
            for(int i = 0;i < num;i++){
                String columnName = rsmd.getColumnName(i);
                map.put(columnName,rs.getString(columnName));
            }
            result.add(map);
        }
        System.out.println(result.size());
    }
}
