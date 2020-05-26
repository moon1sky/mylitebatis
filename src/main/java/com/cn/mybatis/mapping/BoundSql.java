package com.cn.mybatis.mapping;

import com.cn.mybatis.session.Configuration;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class BoundSql {
    private final Configuration configuration;
    private final String sql;
    private final List<ParameterMapping> parameterMappings;
    private final Object parameterObject;
    private final Map<String, Object> additionalParameters;


    public BoundSql(Configuration configuration, String sql, Object parameterObject) {
        this.configuration = configuration;
        this.sql = sql;
        this.parameterObject = parameterObject;
        this.parameterMappings = null;
        this.additionalParameters = null;
    }

    public BoundSql(String sql, List<ParameterMapping> parameterMappings, Object parameterObject) {
        this.configuration = new Configuration();
        this.sql = sql;
        this.parameterObject = parameterObject;
        this.parameterMappings = null;
        this.additionalParameters = null;
    }
}
