package com.cn.mybatis.io;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.Reader;

public class DocumentReader {

    public static Document createDocument(Reader reader) {
        Document dom;
        SAXReader saxReader = new SAXReader();
        try {
            dom = saxReader.read(reader);
        } catch (DocumentException e) {
            throw new RuntimeException("parse xml failed");
        }
        return dom;
    }
}
