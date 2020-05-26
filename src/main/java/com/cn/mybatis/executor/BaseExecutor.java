package com.cn.mybatis.executor;

import com.cn.mybatis.mapping.BoundSql;
import com.cn.mybatis.mapping.MappedStatement;
import com.cn.mybatis.session.Configuration;
import com.cn.mybatis.session.RowBounds;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class BaseExecutor implements Executor {

    protected Executor wrapper;

    protected Configuration configuration;

    private boolean closed;

    protected BaseExecutor(Configuration configuration) {
        this.closed = false;
        this.configuration = configuration;
        this.wrapper = this;
    }

    protected void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    protected abstract <E> List<E> doQuery(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql)
            throws SQLException;

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException  {
        BoundSql boundSql = ms.getBoundSql(parameter);
        return query(ms, parameter, rowBounds, resultHandler, boundSql);
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        ErrorContext.instance().resource(ms.getResource()).activity("executing a query").object(ms.getId());
        return queryFromDatabase(ms, parameter, rowBounds, resultHandler, boundSql);
    }

    private <E> List<E> queryFromDatabase(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        return doQuery(ms, parameter, rowBounds, resultHandler, boundSql);
    }
}
