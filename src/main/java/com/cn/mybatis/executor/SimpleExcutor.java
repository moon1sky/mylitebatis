package com.cn.mybatis.executor;

import com.cn.mybatis.executor.statement.StatementHandler;
import com.cn.mybatis.mapping.BoundSql;
import com.cn.mybatis.mapping.MappedStatement;
import com.cn.mybatis.session.Configuration;
import com.cn.mybatis.session.RowBounds;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SimpleExcutor extends BaseExecutor {

    protected SimpleExcutor(Configuration configuration) {
        super(configuration);
    }

    @Override
    public <E> List<E> doQuery(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        Statement stmt = null;
        try {
            Configuration configuration = ms.getConfiguration();
            StatementHandler handler = configuration.newStatementHandler(wrapper, ms, parameter, rowBounds, resultHandler, boundSql);
            stmt = prepareStatement(handler,configuration);
            return handler.query(stmt, resultHandler);
        } finally {
            closeStatement(stmt);
        }
    }

    private Statement prepareStatement(StatementHandler handler,Configuration configuration) throws SQLException {
        Statement stmt;
        Connection connection = configuration.getConnection();
        stmt = handler.prepare(connection,1000);
        handler.parameterize(stmt);
        return stmt;
    }


}
