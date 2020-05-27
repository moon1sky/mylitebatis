package com.mybatis.v1;

import com.mybatis.v1.entity.User;

/**
 * 操作JDBC
 */
public class SimpleExcutor implements Excutor{

    /**
     * JDBC方法
     *
     * @param statement
     * @param params
     * @param <T>
     * @return
     */

    @Override
    public <T> T selectOne(String statement, String params) {



        /**
         * JDBC 操作数据库返回对象
         */
        User user = new User();
        user.setName("ceshi");
        return (T) user;
    }
}
