package com.cn.mybatis.plugin;

public interface Interceptor {

    Object preIntercept(Object o);

    Object afterIntercept(Object o);

}
