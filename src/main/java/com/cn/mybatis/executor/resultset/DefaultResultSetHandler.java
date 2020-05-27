package com.cn.mybatis.executor.resultset;

import com.cn.mybatis.executor.ErrorContext;
import com.cn.mybatis.executor.Executor;
import com.cn.mybatis.executor.ResultHandler;
import com.cn.mybatis.executor.parameter.ParameterHandler;
import com.cn.mybatis.mapping.*;
import com.cn.mybatis.session.*;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

public class DefaultResultSetHandler<T> implements ResultSetHandler {

    private static final Object DEFERRED = new Object();

    private final Executor executor;
    private final Configuration configuration;
    private final MappedStatement mappedStatement;
    private final RowBounds rowBounds;
    private final ParameterHandler parameterHandler;
    private final ResultHandler<?> resultHandler;
    private final BoundSql boundSql;

    public DefaultResultSetHandler(Executor executor, Configuration configuration, MappedStatement mappedStatement,
                                   RowBounds rowBounds, ParameterHandler parameterHandler, ResultHandler<?> resultHandler, BoundSql boundSql) {
        this.executor = executor;
        this.configuration = configuration;
        this.mappedStatement = mappedStatement;
        this.rowBounds = rowBounds;
        this.parameterHandler = parameterHandler;
        this.resultHandler = resultHandler;
        this.boundSql = boundSql;
    }

    @Override
    public List handleResultSets(Statement stmt) throws SQLException {
        ResultSet resultSet = stmt.executeQuery(boundSql.getSql());
        List<T> results = new ArrayList<T>();
        Class<?> resultTypeClass = mappedStatement.getResultTypeClass();
        try {
            while (resultSet.next()) {
                Object resultObject = resultTypeClass.newInstance();
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                int columnCount = resultSetMetaData.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    Field field = resultTypeClass.getDeclaredField(resultSetMetaData.getColumnLabel(i));
                    field.setAccessible(true);
                    field.set(resultObject, resultSet.getObject(i));
                }
                results.add((T)resultObject);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return results;
    }


}
