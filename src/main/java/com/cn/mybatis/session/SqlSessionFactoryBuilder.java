package com.cn.mybatis.session;

import com.cn.mybatis.builder.XMLConfigBuilder;
import com.cn.mybatis.executor.ErrorContext;
import com.cn.mybatis.session.defaults.DefaultSqlSessionFactory;

import java.io.IOException;
import java.io.Reader;

public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(Reader reader) {
        try {
            XMLConfigBuilder parser = new XMLConfigBuilder(reader);
            Configuration configuration = parser.parse();
            return build(configuration);
        } catch (Exception e) {
            throw new RuntimeException("Error building SqlSession.", e);
        } finally {
            ErrorContext.instance().reset();
            try {
                reader.close();
            } catch (IOException e) {
                // Intentionally ignore. Prefer previous error.
            }
        }
    }

    public SqlSessionFactory build(Configuration config) {
        return new DefaultSqlSessionFactory(config);
    }
}
