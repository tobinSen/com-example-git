//package com.example.spring.dynamic.aop.decoder;
//
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.TypeReference;
//import org.springframework.beans.factory.annotation.Value;
//
//import java.util.Map;
//
//public class MapDecoder {
//
//    public Map<String, Object> decodeMap(String value) {
//        return (Map<String, Object>) JSONObject.parse(value, new TypeReference<Map<String, Object>>(){});
//    }
//
//    //@Value("#{T(com.example.spring.dynamic.aop.decoder.MapDecoder).decodeMap('${test.map1:}')")
//}
