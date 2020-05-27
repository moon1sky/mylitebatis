package com.mybatis.v1;

import com.mybatis.v1.dao.UserMapper;
import com.mybatis.v1.entity.User;

public class Admin {
    public static void main(String[] args) {



        SqlSession sqlSession = new SqlSession(new Configure(),new SimpleExcutor());
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = (User) mapper.selectUserById("1");
        System.out.println("user="+user);

    }
}
