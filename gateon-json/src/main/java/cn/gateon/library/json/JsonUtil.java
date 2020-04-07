package cn.gateon.library.json;

import cn.gateon.library.common.exception.GateonException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapLikeType;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since v1.1
 */
public class JsonUtil {

    /**
     * 默认转换器
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 忽略null值的转换器
     */
    private static final ObjectMapper NOT_NULL_MAPPER = new ObjectMapper();

    static {
        MAPPER.configure(MapperFeature.USE_ANNOTATIONS, true);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        NOT_NULL_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        NOT_NULL_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        NOT_NULL_MAPPER.configure(MapperFeature.USE_ANNOTATIONS, true);
    }

    public static ObjectMapper getInstance() {
        return new ObjectMapper();
    }

    /**
     * @param json  json字符串
     * @param clazz 目标对象的类
     * @param <T>   泛型
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            throw new GateonException("解析json失败", e);
        }
    }

    public static <T> T fromJsonBytes(byte[] json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            throw new GateonException("解析json失败", e);
        }
    }

    public static <T> T fromJson(String json, JavaType type) {
        try {
            return MAPPER.readValue(json, type);
        } catch (IOException e) {
            throw new GateonException("解析json失败", e);
        }
    }

    public static <T> T fromJsonBytes(byte[] json, JavaType type) {
        try {
            return MAPPER.readValue(json, type);
        } catch (IOException e) {
            throw new GateonException("解析json失败", e);
        }
    }

    public static <T> T fromJson(Reader reader, Class<T> clazz) {
        try {
            return MAPPER.readValue(reader, clazz);
        } catch (IOException e) {
            throw new GateonException("解析json失败", e);
        }
    }

    /**
     * bean 转换为 json
     *
     * @param bean
     * @return
     */
    public static String toJson(Object bean) {
        try {
            return MAPPER.writeValueAsString(bean);
        } catch (JsonProcessingException e) {
            throw new GateonException("解析json失败", e);
        }
    }


    /**
     * 忽略null字段
     *
     * @param o
     * @return
     */
    public static String toJsonNotNull(Object o) {
        try {
            return NOT_NULL_MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new GateonException("解析json失败", e);
        }
    }

    public static byte[] toJsonBytesNotNull(Object o) {
        try {
            return NOT_NULL_MAPPER.writeValueAsBytes(o);
        } catch (JsonProcessingException e) {
            throw new GateonException("解析json失败", e);
        }
    }


    /**
     * json 转 集合
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> List<T> toList(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, clazz));
        } catch (IOException e) {
            throw new GateonException("解析json失败", e);
        }
    }

    public static <V> Map<String, V> fromJsonToMap(String json, Class<? extends Map> mapClass, Class<V> vClass) {
        try {
            MapLikeType mapLikeType = MAPPER.getTypeFactory().constructMapType(mapClass, String.class, vClass);
            return MAPPER.readValue(json, mapLikeType);
        } catch (IOException e) {
            throw new GateonException("解析json失败", e);
        }
    }

    public static <T> T fromJson(InputStream inputStream, Class<T> importBeanClass) {
        try {
            return MAPPER.readValue(inputStream, importBeanClass);
        } catch (IOException e) {
            throw new GateonException("解析json失败", e);
        }
    }

    public static <T> LinkedList<T> toLinkedList(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, MAPPER.getTypeFactory().constructCollectionType(LinkedList.class, clazz));
        } catch (IOException e) {
            throw new GateonException("解析json失败", e);
        }
    }
}