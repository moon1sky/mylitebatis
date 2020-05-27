package com.mybatis.v1;

public class SqlSession {

    private Configure configure;

    private Excutor excutor;

    public SqlSession(Configure configure, Excutor excutor) {
        this.configure = configure;
        this.excutor = excutor;
    }


    public <T> T getMapper(Class<T> classZZ) {
        return configure.getMapper(classZZ,this);
    }

    public <T> T selectOne(String statement, String params) {
        return excutor.selectOne(statement,params);

    }

}
