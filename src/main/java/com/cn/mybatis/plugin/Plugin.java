package com.cn.mybatis.plugin;

import com.cn.mybatis.Annotation.Intercepts;
import com.cn.mybatis.Annotation.Signature;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Plugin implements InvocationHandler {

    private Object target;
    private Interceptor interceptor;
    private Map<Class<?>, Set<Method>> signatureMap;


    public Plugin(Interceptor interceptor, Object target, Map<Class<?>, Set<Method>> signatureMap) {
        this.interceptor = interceptor;
        this.target = target;
        this.signatureMap = signatureMap;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Set<Method> methods = signatureMap.get(method.getDeclaringClass());
        //指定的接口及方法，才可以被代理执行
        if(methods!=null && methods.contains(method)){
            interceptor.preIntercept(target);
            Object result = method.invoke(target, args);
            interceptor.afterIntercept(result);
            return result;
        }
        return method.invoke(target,args);
    }

    public static Object wrap(Object target, Interceptor interceptor) {
        Map<Class<?>, Set<Method>> signatureMap = getSignatureMap(interceptor);
        Class<?> type = target.getClass();
        Class<?>[] interfaces = getAllInterfaces(type, signatureMap);
        if (interfaces.length > 0) {
            return Proxy.newProxyInstance(
                    type.getClassLoader(),
                    interfaces,
                    new Plugin(interceptor, target, signatureMap));
        }
        return target;
    }

    private static Map<Class<?>, Set<Method>> getSignatureMap(Interceptor interceptor) {
        Intercepts annotation = interceptor.getClass().getAnnotation(Intercepts.class);
        if (annotation == null) {
            throw new RuntimeException("No @Intercepts annotation was found in interceptor " + interceptor.getClass().getName());
        }
        Signature[] sigs = annotation.value();

        Map<Class<?>, Set<Method>> signatureMap = new HashMap<>();
        for (Signature sig : sigs) {
            Class<?> type = sig.type();
            Set<Method> methods = signatureMap.get(type);
            if(methods==null){
                methods = new HashSet<>();
                signatureMap.put(type,methods);
            }
            try {
                Method declaredMethod = type.getDeclaredMethod(sig.method(), sig.args());
                methods.add(declaredMethod);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("method "+sig.method()+" not find in class "+sig.type().getName());
            }
        }
        return signatureMap;
    }

    private static Class<?>[] getAllInterfaces(Class<?> type, Map<Class<?>, Set<Method>> signatureMap) {
        Set<Class> set = new HashSet<>();
        while (type != null) {
            for (Class a : type.getInterfaces()) {
                //不要生成多余的代理类,只有指定的接口才可以生成代理类
                if (signatureMap.containsKey(a)) {
                    set.add(a);
                }
            }
            type = type.getSuperclass();
        }
        return set.toArray(new Class[set.size()]);
    }

}
