package com.mybatis.v1;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class Configure {


    public <T> T getMapper(Class<T> classZZ, SqlSession sqlSession) {

        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[]{classZZ},
                new MapperProxy(sqlSession));

    }


    static class ConfigureXML{
        public final static String NAME_SPACE = "com";

        public static Map map = new HashMap<>();

        static {
            map.put(NAME_SPACE,"select * from user where id = 1");
        }

    }

}
