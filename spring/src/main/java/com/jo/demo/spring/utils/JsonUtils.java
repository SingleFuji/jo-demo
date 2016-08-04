package com.jo.demo.spring.utils;

import java.io.StringWriter;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;

/**
 * json工具类
 * 
 * @author wujun
 */
public final class JsonUtils {

    private static final Logger       logger  = LoggerFactory.getLogger(JsonUtils.class);

    private static final Gson         GSON    = new Gson();
    private static final ObjectMapper JACKSON = new ObjectMapper();

    /**
     * jackson 对象转json字符串
     * 
     * @param obj
     * @return
     * @see ObjectMapper#writeValue(java.io.Writer, Object)
     */
    public static String objectToJson(Object obj) {
        try {

            StringWriter str = new StringWriter();
            JACKSON.writeValue(str, obj);
            return str.getBuffer().toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * jackson json字符串转对象
     * 
     * @param Data
     * @param classes
     * @return
     * @see ObjectMapper#readValue(String, Class)
     */
    public static <T> T jsonToObject(String content, Class<T> classes) {
        try {
            return JACKSON.readValue(content, classes);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * gson 对象转json字符串
     * 
     * @see com.google.gson.Gson#toJson(Object)
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        return GSON.toJson(obj);
    }

    /**
     * 把对象转换成json字符串，可以配置需要忽略的属性
     * 
     * @param object 待转换的对象
     * @param ignoreProperties 需要忽略的属性list
     * @return json格式的字符串
     */
    public static String toJsonIgnore(Object object, final List<String> ignoreProperties) {
        if (ignoreProperties == null || ignoreProperties.size() == 0) {
            return toJson(object);
        }

        return new GsonBuilder().serializeNulls()
                .setDateFormat(DateUtils.STD_FULL_DATE_TIME)
                .setLongSerializationPolicy(LongSerializationPolicy.STRING)
                .disableHtmlEscaping()
                .setExclusionStrategies(new ExclusionStrategy() {

                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        String propName = null;
                        for (int i = 0, len = ignoreProperties.size(); i < len; i++) {
                            propName = ignoreProperties.get(i);
                            if (f.getName().equals(propName)) {
                                return true;
                            }
                        }
                        return false;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                }).create().toJson(object);
    }

    /**
     * gson json字符串转对象
     * 
     * @see com.google.gson.Gson#fromJson(String, Class)
     * @param str
     * @param clazz
     * @return
     */
    public static <T> T fromJson(String str, Class<T> clazz) {
        return (T) GSON.fromJson(str.trim(), clazz);
    }


    /**
     * ali fastjson 对象转json字符串
     * 
     * @param obj
     * @return
     * @see JSON#toJSON(Object, com.alibaba.fastjson.parser.ParserConfig)
     */
    public static String writeValue(Object obj) {
        return JSON.toJSONString(obj, new SerializerFeature[] {SerializerFeature.DisableCircularReferenceDetect});
    }

    /**
     * ali fastjson json字符串转对象
     * 
     * @param json
     * @param type
     * @return
     * @see JSON#parseObject(String, Class)
     */
    public static <T> T readValue(String json, Class<T> type) {
        return JSON.parseObject(json, type);
    }

    private JsonUtils() {
    }
}
