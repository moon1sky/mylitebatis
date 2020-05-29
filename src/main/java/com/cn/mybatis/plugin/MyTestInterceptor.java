package com.cn.mybatis.plugin;


import com.cn.mybatis.Annotation.Intercepts;
import com.cn.mybatis.Annotation.Signature;
import com.cn.mybatis.executor.Executor;
import com.cn.mybatis.executor.ResultHandler;
import com.cn.mybatis.mapping.BoundSql;
import com.cn.mybatis.mapping.MappedStatement;
import com.cn.mybatis.session.RowBounds;

@Intercepts({@Signature(type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
        ,
        @Signature(type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, BoundSql.class})})
public class MyTestInterceptor implements Interceptor,DummyInterface {

    @Override
    public Object preIntercept(Object o) {
        System.out.println("test MyTestInterceptor preIntercept.......");
        return null;
    }

    @Override
    public Object afterIntercept(Object o) {
        System.out.println("test MyTestInterceptor afterIntercept......." + o);
        return null;
    }

    @Override
    public void dummy() {
        System.out.println("test dummy method.......");
    }

//    @Override
//    public Object intercept(Invacation invacation) {
//
//        System.out.println("test MyTestInterceptor start.......");
//
//        invacation.process();
//
//        System.out.println("test MyTestInterceptor end.......");
//
//        return null;
//    }


}
