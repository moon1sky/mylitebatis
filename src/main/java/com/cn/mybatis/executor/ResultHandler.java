package com.cn.mybatis.executor;

public interface ResultHandler<T> {

  void handleResult(ResultContext<? extends T> resultContext);

}
