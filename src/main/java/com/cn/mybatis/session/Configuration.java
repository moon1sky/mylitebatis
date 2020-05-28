package com.cn.mybatis.session;

import com.alibaba.druid.pool.DruidDataSource;
import com.cn.mybatis.executor.Executor;
import com.cn.mybatis.executor.ResultHandler;
import com.cn.mybatis.executor.SimpleExcutor;
import com.cn.mybatis.executor.parameter.DefaultParameterHandler;
import com.cn.mybatis.executor.parameter.ParameterHandler;
import com.cn.mybatis.executor.resultset.DefaultResultSetHandler;
import com.cn.mybatis.executor.resultset.ResultSetHandler;
import com.cn.mybatis.executor.statement.RoutingStatementHandler;
import com.cn.mybatis.executor.statement.StatementHandler;
import com.cn.mybatis.mapping.BoundSql;
import com.cn.mybatis.mapping.MappedStatement;
import com.cn.mybatis.plugin.Interceptor;
import com.cn.mybatis.plugin.InterceptorChains;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Configuration {

    protected ExecutorType defaultExecutorType = ExecutorType.SIMPLE;

    protected Connection connection;
    protected DataSource dataSource;

    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();

    protected final InterceptorChains interceptorChains = new InterceptorChains();

    public Configuration() {

    }


    public ExecutorType getDefaultExecutorType() {
        return defaultExecutorType;
    }


    public void addMappedStatement(MappedStatement ms) {
        mappedStatements.put(ms.getId(), ms);
    }

    public Collection<MappedStatement> getMappedStatements() {
        return mappedStatements.values();
    }

    public Collection<String> getMappedStatementNames() {
        return mappedStatements.keySet();
    }

    public MappedStatement getMappedStatement(String id) {
        return mappedStatements.get(id);
    }


    public StatementHandler newStatementHandler(Executor executor, MappedStatement mappedStatement, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        StatementHandler statementHandler = new RoutingStatementHandler(executor, mappedStatement, parameterObject, rowBounds, resultHandler, boundSql);
        //TODO代理 Handler
        return statementHandler;
    }

    public void addMapStatement(MappedStatement mappedStatement) {
        this.mappedStatements.put(mappedStatement.getId(), mappedStatement);
    }

    public Executor newExecutor(ExecutorType executorType) {
        executorType = executorType == null ? defaultExecutorType : executorType;
        //这句再做一下保护,囧,防止粗心大意的人将defaultExecutorType设成null?
        executorType = executorType == null ? ExecutorType.SIMPLE : executorType;
        Executor executor=null;
        //然后就是简单的3个分支，产生3种执行器BatchExecutor/ReuseExecutor/SimpleExecutor
        if (ExecutorType.BATCH == executorType) {
//            executor = new BatchExecutor();
        } else if (ExecutorType.REUSE == executorType) {
//            executor = new ReuseExecutor();
        } else {
            executor = new SimpleExcutor(this);
        }
        if(executor==null){
            executor = new SimpleExcutor(this);
        }
        return (Executor) interceptorChains.pluginAll(executor);
    }

    public Integer getDefaultFetchSize() {
        return 1000000;
    }

    public Integer getDefaultStatementTimeout() {
        return 1000000;
    }

    public ParameterHandler newParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql) {
        this.mappedStatements.put(mappedStatement.getId(), mappedStatement);
//        this.parameterObject = parameterObject;
        return new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
    }

    public ResultSetHandler newResultSetHandler(Executor executor, MappedStatement
            mappedStatement, RowBounds rowBounds, ParameterHandler parameterHandler, ResultHandler resultHandler, BoundSql boundSql) {
        return new DefaultResultSetHandler(executor, this, mappedStatement, rowBounds, parameterHandler, resultHandler, boundSql);
    }

    public void allPlugins(Interceptor interceptor) {
        interceptorChains.add(interceptor);
    }

    public List<Interceptor> getAllPlugins() {
        return interceptorChains.getAllPlugins();
    }

    public void setDataSource(DruidDataSource datasource) {
        this.dataSource = datasource;
    }


    public Connection getConnection() throws SQLException {
        if (connection == null) {
            openConnection();
        }
        return connection;
    }

    protected void openConnection() throws SQLException {
//        if (log.isDebugEnabled()) {
//            log.debug("Opening JDBC Connection");
//        }
        connection = dataSource.getConnection();
//        if (level != null) {
//            connection.setTransactionIsolation(level.getLevel());
//        }
//        setDesiredAutoCommit(autoCommit);
    }
}
