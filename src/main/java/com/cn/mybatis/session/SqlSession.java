package com.cn.mybatis.session;


import java.sql.Connection;
import java.util.List;

public interface SqlSession {

  <T> T selectOne(String statement);

  <T> T selectOne(String statement, Object parameter);

  <E> List<E> selectList(String statement);

  <E> List<E> selectList(String statement, Object parameter);

  <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds);

  Configuration getConfiguration();

  <T> T getMapper(Class<T> type);

  Connection getConnection();
}
