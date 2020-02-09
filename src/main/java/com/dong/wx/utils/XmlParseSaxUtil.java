package com.dong.wx.utils;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * xml sax解析工具类
 *
 * @Author dongzhihua
 * @Date 2020-02-05 22:18
 */
public abstract class XmlParseSaxUtil {

    /**
     * 解析xml文件内容为map
     *
     * @Author dongzhihua
     * @Date 2020-02-06 09:44
     */
    public static <T> T parse(File xmlFile, Class<T> cla) {

        return JsonUtils.mapToBean(parseToMap(xmlFile), cla);
    }

    /**
     * 解析xml为Map
     * @Author dongzhihua
     * @Date 2020-02-06 09:57
     */
    public static Map<String, String> parseToMap(File xmlFile) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(xmlFile);
            return parseToMap(fis);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(fis);
        }
    }

    /**
     * 解析xml流为bean
     * @Author dongzhihua
     * @Date 2020-02-06 09:58
     */
    public static <T> T parse(InputStream xmlIn, Class<T> cla) {

        return JsonUtils.mapToBean(parseToMap(xmlIn), cla);
    }

    /**
     * 解析xml流为map
     * @Author dongzhihua
     * @Date 2020-02-06 09:58
     */
    public static Map<String, String> parseToMap(InputStream xmlIn) {
        try {
            // 1、创建一个SAX解析工厂对象
            final SAXParserFactory spy = SAXParserFactory.newInstance();
            // 2、通过工厂对象获取SAX解析对象
            final SAXParser parser = spy.newSAXParser();
            // 3、加载xml文件
            MapHandler han = new MapHandler();
            parser.parse(xmlIn, han);
            return han.obj;
        } catch (Exception e) {
            throw new RuntimeException("解析失败", e);
        }
    }

    /**
     * 解析为map的Handler
     *
     * @Author dongzhihua
     * @Date 2020-02-06 09:45
     */
    public static class MapHandler extends DefaultHandler {
        private Map<String, String> obj = new HashMap<>();
        private String currentTag = null; // 记录解析时的上一个节点名称

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            currentTag = qName;
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            if (ch == null || ch.length == 0) {
                return;
            }
            String content = new String(ch, start, length);
            if (!StringUtils.hasText(content)) {
                return;
            }
            LoggerFactory.getLogger(MapHandler.class).debug("tagInfo - {}: {}", currentTag, content);
            this.obj.put(firstLower(currentTag), content.trim());
        }
    }

    /**
     * 第一个字符大写
     * @Author dongzhihua
     * @Date 2020-02-06 09:58
     */
    private static String firstLower(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }
}
