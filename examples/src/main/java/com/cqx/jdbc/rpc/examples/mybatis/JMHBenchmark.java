package com.cqx.jdbc.rpc.examples.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 自己垃圾公司电脑上用JMH进行测试
 * Benchmark                 Mode    Cnt    Score                 Error      Units
 * DriverBenchmark.test      thrpt   10    897.688± 45.740          0        ops/s
 */
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 3)
@Measurement(iterations = 10, time = 5, timeUnit = TimeUnit.SECONDS)
@Threads(20)
@Fork(1)
@OutputTimeUnit(TimeUnit.SECONDS)
public class JMHBenchmark {

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

    @Benchmark
    public void test() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = sqlSession.selectOne("com.cqx.jdbc.rpc.examples.mybatis.UserMapper.selectById", 1);
        List<User> users = sqlSession.selectList("com.cqx.jdbc.rpc.examples.mybatis.UserMapper.selectAll");
        sqlSession.close();
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(JMHBenchmark.class.getSimpleName())
                .output("E:/Benchmark1.log")
                .build();
        new Runner(options).run();
    }
}
