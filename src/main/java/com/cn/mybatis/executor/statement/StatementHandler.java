package com.cn.mybatis.executor.statement;

import com.cn.mybatis.executor.ResultHandler;
import com.cn.mybatis.mapping.BoundSql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author Clinton Begin
 */
public interface StatementHandler {

  Statement prepare(Connection connection, Integer transactionTimeout)
      throws SQLException;

  void parameterize(Statement statement)
      throws SQLException;

  <E> List<E> query(Statement statement, ResultHandler resultHandler)
      throws SQLException;

  BoundSql getBoundSql();

  ParameterHandler getParameterHandler();

}
