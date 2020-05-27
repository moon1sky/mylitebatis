package com.cn.mybatis.session;

import com.cn.mybatis.entity.User;
import com.cn.mybatis.io.Resources;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.io.Reader;
import java.util.List;

public class SqlSessionTest {

    static SqlSessionFactory sqlSessionFactory;

    @Before
    public void setup() throws Exception {
//        createBlogDataSource();
//        final String resource = "org/apache/ibatis/builder/MapperConfig.xml";
        final String resource = "mybatis-config.xml";
        final Reader reader = Resources.getResourceAsReader(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }

    @Test
    public void selectUser() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
//        sqlSession.selectOne("select * from user where 1=1");
        List<User> list = sqlSession.selectList("selectById",1);
        System.out.println("list="+list);

    }


}
