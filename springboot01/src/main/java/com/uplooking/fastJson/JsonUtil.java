package com.uplooking.fastJson;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class JsonUtil {

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        //全局的设置序列化
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        //独立设置序列化（会进行覆盖）
        objectMapper.getSerializationConfig().with(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        objectMapper.getDeserializationConfig().with(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                //禁用一部分逻辑，反序列话没有的属性，默认会报错
                .without(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //等效objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 将对象转为json
     *
     * @param o
     * @return
     */
    public static String toJSON(Object o) {
        try {
            /**
             * 将对象的大写转换为下划线加小写，例如：userName-->user_name
             * 将下划线转换为驼峰的形式，例如：user_name-->userName
             */
            //序列化的时候，坚持驼峰命名法
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            return objectMapper.writeValueAsString(o);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 将json转为对象
     *
     * @param json
     * @param objClass
     * @param <T>
     * @return
     */
    public static <T> T toObject(String json, Class<T> objClass) {
        try {
            return objectMapper.readValue(json, objClass);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 将json转为对象集合
     *
     * @param json
     * @param ObjClass
     * @param <T>
     * @return
     */
    public static <T> List<T> toObjectList(String json, Class<T> ObjClass) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, ObjClass);
            return objectMapper.readValue(json, javaType);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 将json转换为集合
     *
     * @param json
     * @param <T>
     * @return
     */
    public static <T> List<T> toObjectList(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<T>>() {
            });
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static Map<String, Object> toMap(String json) {
        try {

            return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {
            });
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Person person = new Person();
        person.setId(1L);
        person.setAge(12);
//        person.setName("张三");
        person.setTime(new Date());
        String json = JsonUtil.toJSON(person);
        System.out.println(json);

        String s = "{\"id\":1,\"name\":\"张三\",\"age\":12,\"time\":\"2019-06-11 14:59:29\"}";
        Person object = JsonUtil.toObject(json, Person.class);
        System.out.println(object);


    }

}
