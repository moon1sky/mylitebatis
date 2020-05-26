package com.cn.mybatis.builder;

import com.cn.mybatis.io.DocumentReader;
import com.cn.mybatis.mapping.MappedStatement;
import com.cn.mybatis.mapping.SqlSource;
import com.cn.mybatis.mapping.StatementType;
import com.cn.mybatis.mapping.defaults.DefaultSqlSource;
import com.cn.mybatis.session.Configuration;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.InputStream;
import java.io.Reader;
import java.util.List;


@Data
@AllArgsConstructor
public class XmlMapperBuilder {

    private Reader reader;
    private Configuration configuration;

    public void parse() {
        Document document = DocumentReader.createDocument(reader);
        parseMapperElement(document.getRootElement());
    }
    @SuppressWarnings("unchecked")
    private void parseMapperElement(Element rootElement) {
        String namespace = rootElement.attributeValue("namespace");
        try {
            if (namespace == null || "".equals(namespace)) {
                throw new Exception("namespace is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        parseStatementElements(rootElement.elements("select"));
    }

    private void parseStatementElements(List<Element> select) {
        for (Element element: select) {
            parseStatementElement(element);
        }
    }

    private void parseStatementElement(Element element) {
        String id = element.attributeValue("id");

        String parameterType = element.attributeValue("parameterType");
        Class<?> parameterTypeClass = resolveClass(parameterType);

        String resultType = element.attributeValue("resultType");
        Class<?> resultTypeClass = resolveClass(resultType);

        String statementTypeString = element.attributeValue("statementType") == null ? "prepared"
                : element.attributeValue("statementType");

        StatementType statementType = "prepared".equals(statementTypeString) ? StatementType.PREPARED : StatementType.STATEMENT;

        SqlSource sqlSource = createSqlSource(element);
        MappedStatement mappedStatement = new MappedStatement(configuration, id, statementType, sqlSource);
        configuration.addMapStatement(mappedStatement);
    }

    private SqlSource createSqlSource(Element element) {
        String text = element.getTextTrim();
        return new DefaultSqlSource(text);
    }

    private Class<?> resolveClass(String parameterType) {
        try {
            return Class.forName(parameterType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
