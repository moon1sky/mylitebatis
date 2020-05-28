package com.cn.mybatis.builder;

import com.cn.mybatis.io.DocumentReader;
import com.cn.mybatis.io.Resources;
import com.cn.mybatis.plugin.Interceptor;
import com.cn.mybatis.session.Configuration;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Properties;

import com.alibaba.druid.pool.DruidDataSource;

public class XMLConfigBuilder {

    private final Document document;
    private Reader reader;
    private Configuration configuration;

    public XMLConfigBuilder(Reader reader) {
        this.configuration = new Configuration();
        this.reader = reader;
        this.document = DocumentReader.createDocument(reader);
    }

    public Configuration parse() {
        try {
            Element rootElement = document.getRootElement();
            parseEnvironmentsElement(rootElement.element("environments"));
            pluginElement(rootElement.element("plugins"));
            parseMappersElement(rootElement.element("mappers"));
            return configuration;
        }catch (Exception e){
            throw new RuntimeException("Error parsing SQL Mapper Configuration. Cause: " + e, e);
        }
    }

    private void parseMappersElement(Element mappers) {
        List<Element> mapperElements = mappers.elements();
        for (Element element : mapperElements) {
            String resource = element.attributeValue("resource");
            Reader reader = Resources.getResourceAsReader(resource);
            XmlMapperBuilder xmlMapperBuilder = new XmlMapperBuilder(reader, configuration);
            xmlMapperBuilder.parse();
        }


    }

    private void pluginElement(Element plugins) throws Exception{
        List<Element> mapperElements = plugins.elements();
        for (Element element : mapperElements) {
            String interceptor = element.attributeValue("interceptor");
            Interceptor o = (Interceptor) resolveTypeClass(interceptor).getDeclaredConstructor().newInstance();
            configuration.allPlugins(o);
        }
    }


    private void parseEnvironmentsElement(Element element) {
        String defaultEnvironment = element.attributeValue("default");
        List<Element> environmentList = element.elements();
        for (Element environment : environmentList) {
            String environmentId = environment.attributeValue("id");
            if (defaultEnvironment.equals(environmentId)) {
                createDataSource(environment);
            }
        }
    }

    private void createDataSource(Element element) {
        Element dataSource = element.element("dataSource");

        String dataSourceType = dataSource.attributeValue("type");
        List<Element> propertyElements = dataSource.elements();

        Properties properties = new Properties();

        for (Element property : propertyElements) {
            String name = property.attributeValue("name");
            String value = property.attributeValue("value");
            properties.setProperty(name, value);
        }

        DruidDataSource datasource = null;

        if ("Druid".equals(dataSourceType)) {
            datasource = new DruidDataSource();
            datasource.setDriverClassName(properties.getProperty("driver"));
            datasource.setUrl(properties.getProperty("url"));
            datasource.setUsername(properties.getProperty("username"));
            datasource.setPassword(properties.getProperty("password"));
        }
        configuration.setDataSource(datasource);

    }

    private Class<?> resolveTypeClass(String name) {
        Class<?> c;
        try {
            c = Class.forName(name);
        } catch (ClassNotFoundException e) {
            c = null;
        }
        return c;
    }

}
