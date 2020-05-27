package com.cn.mybatis.mapper;

import com.cn.mybatis.entity.User;

public interface UserMapper {

    User selectById(Integer id);
}
