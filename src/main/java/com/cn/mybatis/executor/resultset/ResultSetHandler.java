package com.cn.mybatis.executor.resultset;


import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface ResultSetHandler {

  <T> List<T> handleResultSets(Statement stmt) throws SQLException;

}
