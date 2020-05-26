package com.cn.mybatis.executor.result;

import com.cn.mybatis.executor.ResultContext;
import com.cn.mybatis.executor.ResultHandler;

import java.util.ArrayList;
import java.util.List;

public class DefaultResultHandler implements ResultHandler<Object> {

    private final List<Object> list;

    public DefaultResultHandler() {
        list = new ArrayList<>();
    }

//    @SuppressWarnings("unchecked")
//    public DefaultResultHandler(ObjectFactory objectFactory) {
//        list = objectFactory.create(List.class);
//    }

    @Override
    public void handleResult(ResultContext<?> context) {
        list.add(context.getResultObject());
    }

    public List<Object> getResultList() {
        return list;
    }

}