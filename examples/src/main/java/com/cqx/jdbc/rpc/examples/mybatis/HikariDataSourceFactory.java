package com.cqx.jdbc.rpc.examples.mybatis;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.datasource.DataSourceFactory;

import javax.sql.DataSource;
import java.util.Properties;

public class HikariDataSourceFactory implements DataSourceFactory {
    private HikariConfig hikariConfig;

    @Override
    public void setProperties(Properties properties) {
        HikariConfig config = new HikariConfig();
        config.setPoolName("default");
        config.setJdbcUrl(properties.getProperty("url"));
        config.setUsername(properties.getProperty("username"));
        config.setPassword(properties.getProperty("password"));
        config.setDriverClassName(properties.getProperty("driver"));
        config.setSchema(properties.getProperty("database"));
        config.setDataSourceProperties(properties);
        this.hikariConfig = config;
//        config.setConnectionTimeout(property.getConnectionTimeout());
//        config.setIdleTimeout(property.getIdleTimeout());
//        config.setMaxLifetime(property.getMaxLifetime());
//        config.setMaximumPoolSize(property.getMaxPoolSize());
//        config.setMinimumIdle(property.getMinIdle());
//        config.setConnectionTestQuery(property.getConnectionTestQuery());
//        config.setValidationTimeout(property.getValidationTimeout());
    }

    @Override
    public DataSource getDataSource() {
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        return dataSource;
    }
}
