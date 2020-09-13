package com.example.spring.hutool;

import com.google.common.collect.Maps;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class XmlConfig {
    static Logger logger = LoggerFactory.getLogger(XmlConfig.class);

    public static void main(String[] args) throws Exception {
        System.out.println(getHoneypotsPortNameMap());
    }

    public static List<StoreDcMapping> getStoreDcMappingList() {
        return read("store_dc_mapping", StoreDcMapping.class);
    }

    public static Map<String, String> getHoneypotsPortNameMap() throws Exception {
        Map<String, String> map = Maps.newHashMap();
        Object store_dc_mapping = parseFromXmlFile("resources/store_dc_mapping", StoreDcMapping.class);
        System.out.println(store_dc_mapping);
//        for (StoreDcMapping storeDc : list) {
//            map.put(storeDc.getStoreNo(), storeDc.getDcNo());
//        }
        return map;
    }

    public static <T> List<T> read(String fileName, Class<T> cla, String fieldName, String fieldValue) {
        List<T> result = new ArrayList<T>();
        try {
            SAXReader reader = new SAXReader();
            InputStream in = XmlConfig.class.getClassLoader().getResourceAsStream("config/" + fileName + ".xml");
            Document doc = reader.read(in);
            Element root = doc.getRootElement();
            List<T> list = readNode(root, cla);
            String name = null;
            for (T t : list) {
                Field field = t.getClass().getDeclaredField(fieldName);

                if (field == null) {
                    continue;
                }
                name = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Method method = t.getClass().getMethod("get" + name);
                String value = (String) method.invoke(t);
                if (fieldValue.equals(value)) {
                    result.add(t);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <T> List<T> read(String fileName, Class<T> cla) {
        try {
            SAXReader reader = new SAXReader();
            InputStream in = XmlConfig.class.getClassLoader().getResourceAsStream(fileName + ".xml");
            Document doc = reader.read(in);
            Element root = doc.getRootElement();
            return readNode(root, cla);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> readNode(Element root, Class<T> cla) throws Exception {
        if (root == null) return null;
        List<T> objList = new ArrayList<>();
        // 获取属性   
        List<Attribute> attrs = root.attributes();
        if (attrs != null && attrs.size() > 0) {
            T obj = cla.newInstance();
            for (Attribute attr : attrs) {
                Field f = cla.getDeclaredField(attr.getName());
                f.setAccessible(true);
                if (f.getType() == String.class) {
                    f.set(obj, attr.getValue());
                } else if (f.getType() == Integer.class) {
                    f.set(obj, Integer.valueOf(attr.getValue()));
                }

            }
            objList.add(obj);
        }
        // 获取他的子节点   
        List<Element> childNodes = root.elements();
        for (Element e : childNodes) {
            objList.addAll(readNode(e, cla));
        }
        return objList;
    }

    /**
     * 将Xml文件解析成指定的对象
     *
     * @param xmlFilePath
     * @param objClazz
     * @return
     * @throws JAXBException
     * @throws IOException
     */
    public static Object parseFromXmlFile(String xmlFilePath, Class objClazz) throws JAXBException, IOException {
        FileInputStream fileInputStream = null;
        try {
            JAXBContext context = JAXBContext.newInstance(objClazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            fileInputStream = new FileInputStream(xmlFilePath);
            Object obj = unmarshaller.unmarshal(fileInputStream);
            return obj;
        } catch (Exception e) {
            throw e;
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
    }
}
