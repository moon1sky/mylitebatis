package com.cn.mybatis.plugin;


public class MyTestInterceptor implements Interceptor {
    @Override
    public Object preIntercept(Object o) {
        System.out.println("test MyTestInterceptor preIntercept.......");
        return null;
    }

    @Override
    public Object afterIntercept(Object o) {
        System.out.println("test MyTestInterceptor afterIntercept......."+o);
        return null;
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
