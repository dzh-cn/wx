package com.dong.wx.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

/**
 * @Author: WangYuan
 * @CreateDate: 2019-07-30
 */
public abstract class JacksonUtils {

    public static final String EMPTY_JSON = "{}";

    public static final String EMPTY_JSON_ARRAY = "[]";

    private static final Logger LOGGER = LoggerFactory.getLogger(JacksonUtils.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object object){
        if(object==null){
            return EMPTY_JSON;
        }
        String json = null;
        try {
            json = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            LOGGER.error( String.format("toJson Exception:%s  object=%s",e.getMessage(),object));
            if (object instanceof Collection<?> || object instanceof Iterator<?> || object instanceof Enumeration<?> || object.getClass().isArray()) {
                json = EMPTY_JSON_ARRAY;
            }
        }
        return json;
    }

    public static String toPrettyJson(Object object){
        if(object==null){
            return EMPTY_JSON;
        }
        String json = null;
        try {
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            LOGGER.error( String.format("toPrettyJson Exception:%s  object=%s",e.getMessage(),object));
            if (object instanceof Collection<?> || object instanceof Iterator<?> || object instanceof Enumeration<?> || object.getClass().isArray()) {
                json = EMPTY_JSON_ARRAY;
            }
        }
        return json;
    }


    public static <T> T readValue(String json, Class<T> clazz){
        if(!StringUtils.hasText(json)){
            return null;
        }
        T obj = null;
        try {
            obj = mapper.readValue(json, clazz);
        } catch (IOException e) {
            LOGGER.error(String.format("readValue Exception:%s  json=%s class=%s",e.getMessage(),json,clazz));
        }
        return obj;
    }

    public static Map<String,Object> readMap(String json){
        Map<String,Object> obj = null;
        try {
            obj = mapper.readValue(json, HashMap.class);
        } catch (IOException e) {
            LOGGER.error(String.format("readMap Exception:%s  json=%s",e.getMessage(),json));
        }
        if(obj==null){
            obj = new HashMap<>();
        }
        return obj;
    }

    public static List<?>  readValues(String json, Class<List> listClass, Class<?> clazz){
        if(!StringUtils.hasText(json)){
            return null;
        }
        List<?> obj = null;
        try {
            obj = mapper.readValue(json, getCollectionType(mapper, List.class, clazz));
        } catch (IOException e) {
            LOGGER.error(String.format("readValue Exception:%s  json=%s class=%s",e.getMessage(),json,clazz));
        }
        return obj;
    }

    public static JavaType getCollectionType(ObjectMapper mapper, Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

}
