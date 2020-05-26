package com.cn.mybatis.mapping.defaults;

import com.cn.mybatis.mapping.BoundSql;
import com.cn.mybatis.mapping.ParameterMapping;
import com.cn.mybatis.mapping.SqlSource;
import com.cn.mybatis.session.Configuration;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DefaultSqlSource implements SqlSource {

    private final String sql;
    private final List<ParameterMapping> parameterMappings;
    private final Configuration configuration;

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        return new BoundSql( sql, parameterMappings, parameterObject);
    }
}
