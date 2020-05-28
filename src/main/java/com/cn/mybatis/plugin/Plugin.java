package com.cn.mybatis.plugin;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Plugin implements InvocationHandler {

    private Object target;
    private Interceptor interceptor;


    public Plugin(Interceptor interceptor, Object target) {
        this.interceptor = interceptor;
        this.target = target;
    }

    public static Object wrap(Interceptor interceptor, Object target) {

        Class<?>[] allInterfaces = getAllInterfaces(target.getClass());
        return Proxy.newProxyInstance(Plugin.class.getClassLoader(), allInterfaces, new Plugin(interceptor, target));
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

//        return interceptor.intercept(new Invacation(target,method,args));
        interceptor.preIntercept(target);
        Object result = method.invoke(target, args);
        interceptor.afterIntercept(result);
        return result;
    }

//    public static Object wrap(Object target, Interceptor interceptor) {
//        Map<Class<?>, Set<Method>> signatureMap = getSignatureMap(interceptor);
//        Class<?> type = target.getClass();
//        Class<?>[] interfaces = getAllInterfaces(type, signatureMap);
//        if (interfaces.length > 0) {
//            return Proxy.newProxyInstance(
//                    type.getClassLoader(),
//                    interfaces,
//                    new Plugin(target, interceptor, signatureMap));
//        }
//        return target;
//    }

    private static Class<?>[] getAllInterfaces(Class<?> type) {
        Set<Class<?>> interfaces = new HashSet<>();
        while (type != null) {
            for (Class<?> c : type.getInterfaces()) {
                interfaces.add(c);
            }
            type = type.getSuperclass();
        }
        return interfaces.toArray(new Class<?>[interfaces.size()]);
    }

}
