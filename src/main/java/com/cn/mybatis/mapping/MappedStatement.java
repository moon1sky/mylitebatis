package com.cn.mybatis.mapping;

import com.cn.mybatis.session.Configuration;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MappedStatement {

    private String resource;
    private Configuration configuration;
    private String id;
    private Integer fetchSize;
    private Integer timeout;
    private StatementType statementType;
//    private ResultSetType resultSetType;
    private SqlSource sqlSource;
//    private Cache cache;
//    private ParameterMap parameterMap;
    private boolean flushCacheRequired;
    private boolean useCache;
    private boolean resultOrdered;
    private String[] keyProperties;
    private String[] keyColumns;
    private boolean hasNestedResultMaps;
    private String databaseId;
    private String[] resultSets;

    private Class<?> parameterTypeClass;

    private Class<?> resultTypeClass;

    public MappedStatement(Configuration configuration, String id, StatementType statementType, SqlSource sqlSource) {

    }

    public MappedStatement(Configuration configuration, String id, StatementType statementType, SqlSource sqlSource, Class<?> parameterTypeClass, Class<?> resultTypeClass) {
        this.configuration=configuration;
        this.id=id;
        this.statementType=statementType;
        this.sqlSource=sqlSource;
        this.parameterTypeClass=parameterTypeClass;
        this.resultTypeClass=resultTypeClass;
    }

    public BoundSql getBoundSql(Object parameterObject) {
        BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings == null || parameterMappings.isEmpty()) {
            boundSql = new BoundSql(configuration, boundSql.getSql(),  parameterObject);
        }
        return boundSql;
    }
}
