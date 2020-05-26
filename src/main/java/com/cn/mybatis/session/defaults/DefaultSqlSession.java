package com.cn.mybatis.session.defaults;

import com.cn.mybatis.exception.BindingException;
import com.cn.mybatis.exception.TooManyResultsException;
import com.cn.mybatis.executor.ErrorContext;
import com.cn.mybatis.executor.Executor;
import com.cn.mybatis.mapping.MappedStatement;
import com.cn.mybatis.session.Configuration;
import com.cn.mybatis.session.RowBounds;
import com.cn.mybatis.session.SqlSession;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
public class DefaultSqlSession implements SqlSession {

    private final Configuration configuration;
    private final Executor executor;

    @Override
    public <T> T selectOne(String statement) {
        return selectOne(statement, null);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        List<T> list = this.selectList(statement, parameter);
        if (list.size() == 1) {
            return list.get(0);
        } else if (list.size() > 1) {
            throw new TooManyResultsException("Expected one result (or null) to be returned by selectOne(), but found: " + list.size());
        } else {
            return null;
        }
    }

    @Override
    public <E> List<E> selectList(String statement) {
        return this.selectList(statement, null);
    }

    @Override
    public <E> List<E> selectList(String statement, Object parameter) {
        return this.selectList(statement, null, RowBounds.DEFAULT);
    }

    @Override
    public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds) {
        try {
            MappedStatement ms = configuration.getMappedStatement(statement);
            return executor.query(ms, wrapCollection(parameter), rowBounds,Executor.NO_RESULT_HANDLER);
        } catch (Exception e) {
            throw new RuntimeException("Error querying database.  Cause: " + e, e);
        } finally {
            ErrorContext.instance().reset();
        }
    }

    private Object wrapCollection(final Object object) {
        if (object instanceof Collection) {
            StrictMap<Object> map = new StrictMap<>();
            map.put("collection", object);
            if (object instanceof List) {
                map.put("list", object);
            }
            return map;
        } else if (object != null && object.getClass().isArray()) {
            StrictMap<Object> map = new StrictMap<>();
            map.put("array", object);
            return map;
        }
        return object;
    }

    //more strict
    public static class StrictMap<V> extends HashMap<String, V> {

        @Override
        public V get(Object key) {
            if (!super.containsKey(key)) {
                throw new BindingException("Parameter '" + key + "' not found. Available parameters are " + this.keySet());
            }
            return super.get(key);
        }
    }

    @Override
    public Configuration getConfiguration() {
        return null;
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return null;
    }

    @Override
    public Connection getConnection() {
        return null;
    }
}
