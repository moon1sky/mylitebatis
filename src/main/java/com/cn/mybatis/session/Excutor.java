package com.mybatis.v1;

public interface Excutor {

    public <T> T selectOne(String statement, String params);
}
