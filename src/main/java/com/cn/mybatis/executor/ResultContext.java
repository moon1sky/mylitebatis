package com.cn.mybatis.executor;

public interface ResultContext<T> {

  T getResultObject();

  int getResultCount();

  boolean isStopped();

  void stop();

}
