package com.cqx.jdbc.rpc.examples.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.*;

/**
 * 同JMH的测试环境
 * 线程数  执行时间  总执行次数    QPS
 * 20      200S    大约9500*20   950
 */
public class JavaBenchMark {


    private static final SqlSessionFactory sqlSessionFactory;

    static {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

    }

    private static volatile boolean stop = false;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(30, 30, 1000, TimeUnit.DAYS, new LinkedBlockingQueue<>(10));
        for (int i = 0; i < 20; i++) {
            executorService.submit(() -> {
                int s = 0;
                long start = System.currentTimeMillis();
                while (!stop) {
                    SqlSession sqlSession = sqlSessionFactory.openSession();
                    User user = sqlSession.selectOne("com.cqx.jdbc.rpc.examples.mybatis.UserMapper.selectById", 1);
                    List<User> users = sqlSession.selectList("com.cqx.jdbc.rpc.examples.mybatis.UserMapper.selectAll");
                    sqlSession.close();
                    s++;
                }
                long time = System.currentTimeMillis() - start;
                System.out.println(Thread.currentThread() + "执行次数" + s + "  执行时间:" + time);
            });
        }
        TimeUnit.SECONDS.sleep(200);
        stop = true;
    }
}
