package com.cn.mybatis.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class Resources {

    public static InputStream getResourceAsStream(String resource){
        return getResourceAsStream(resource,getClassLoaders(null));
    }

    public static InputStream getResourceAsStream(String resource,ClassLoader classLoader){
        return getResourceAsStream(resource, getClassLoaders(classLoader));
    }

    public static InputStream getResourceAsStream(String resource, ClassLoader[] classLoader) {
        for (ClassLoader cl : classLoader) {
            if (null != cl) {

                // try to find the resource as passed
                InputStream returnValue = cl.getResourceAsStream(resource);

                // now, some class loaders want this leading "/", so we'll add it and try again if we didn't find the resource
                if (null == returnValue) {
                    returnValue = cl.getResourceAsStream("/" + resource);
                }

                if (null != returnValue) {
                    return returnValue;
                }
            }
        }
        return null;
    }

    public static Reader getResourceAsReader(String resource){
        return new InputStreamReader(getResourceAsStream(resource));
    }


    public static ClassLoader[] getClassLoaders(ClassLoader classLoader) {
        return new ClassLoader[]{
                classLoader,
                Thread.currentThread().getContextClassLoader(),
                Resources.class.getClassLoader()};
    }
}
