package com.cqx.jdbc.rpc.examples.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisTest {

    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = sqlSession.selectOne("com.cqx.jdbc.rpc.examples.mybatis.UserMapper.selectById", 1);
        List<User> users = sqlSession.selectList("com.cqx.jdbc.rpc.examples.mybatis.UserMapper.selectAll");
        sqlSession.close();
    }
}
