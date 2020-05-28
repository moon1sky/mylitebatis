package com.cn.mybatis.plugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InterceptorChains {

    private List<Interceptor> list = new ArrayList<>();

    public void addAll(List<Interceptor> list){
        this.list.addAll(list);
    }

    public void add(Interceptor interceptor){
        this.list.add(interceptor);
    }

    public Object pluginAll(Object target){
        for (Interceptor interceptor : list) {
            return Plugin.wrap(interceptor,target);
        }
        return target;
    }

    public List<Interceptor> getAllPlugins(){
        return Collections.unmodifiableList(list);
    }

}
