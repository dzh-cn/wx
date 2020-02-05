package com.dong.wx.utils;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * xml sax解析工具类
 *
 * @Author dongzhihua
 * @Date 2020-02-05 22:18
 */
public abstract class SaxUtil {

    public static Map<String, String> parseToMap(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            return parseToMap(fis);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(fis);
        }
    }

    public static Map<String, String> parseToMap(InputStream in) {
        try {
            // 1、创建一个SAX解析工厂对象
            final SAXParserFactory spy = SAXParserFactory.newInstance();
            // 2、通过工厂对象获取SAX解析对象
            final SAXParser parser = spy.newSAXParser();
            // 3、加载xml文件
            MapHandler han = new MapHandler();
            parser.parse(in, han);
            return han.obj;
        } catch (Exception e) {
            throw new RuntimeException("解析失败", e);
        }
    }

    public static class MapHandler extends DefaultHandler {
        private Map<String, String> obj;
        private String currentTag = null; // 记录解析时的上一个节点名称
        public MapHandler() {
            obj = new HashMap<>();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            currentTag = qName;
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            if(ch == null || ch.length == 0) {
                return;
            }
            String content = new String(ch, start, length);
            if (!StringUtils.hasText(content)) {
                return;
            }
            LoggerFactory.getLogger(MapHandler.class).info("tag: {}, content: {}", currentTag, content);
            this.obj.put(firstLower(currentTag), content.trim());
        }
    }

    private static String firstLower(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }
}
