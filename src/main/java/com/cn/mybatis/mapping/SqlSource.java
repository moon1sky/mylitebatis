package com.cn.mybatis.mapping;

public interface SqlSource {
    BoundSql getBoundSql(Object parameterObject);
}
