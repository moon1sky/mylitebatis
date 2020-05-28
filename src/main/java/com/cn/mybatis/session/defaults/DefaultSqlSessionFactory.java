package com.cn.mybatis.session.defaults;

import com.cn.mybatis.executor.ErrorContext;
import com.cn.mybatis.executor.Executor;
import com.cn.mybatis.session.Configuration;
import com.cn.mybatis.session.ExecutorType;
import com.cn.mybatis.session.SqlSession;
import com.cn.mybatis.session.SqlSessionFactory;
import com.mysql.cj.exceptions.ExceptionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class DefaultSqlSessionFactory implements SqlSessionFactory {


    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }


    @Override
    public SqlSession openSession() {
        return openSessionFromDataSource(configuration.getDefaultExecutorType(), false);
    }

    @Override
    public SqlSession openSession(boolean autoCommit) {
        return openSessionFromDataSource(configuration.getDefaultExecutorType(), autoCommit);
    }

    @Override
    public SqlSession openSession(Connection connection) {
        return null;
    }


    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    private SqlSession openSessionFromDataSource(ExecutorType execType, boolean autoCommit) {
        try {
            Executor executor = configuration.newExecutor(execType);
            return new DefaultSqlSession(configuration, executor, autoCommit);
        } catch (Exception e) {
            throw new RuntimeException("Error opening session.  Cause: " + e, e);
        } finally {
            ErrorContext.instance().reset();
        }
    }


}
