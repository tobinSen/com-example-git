package com.uplooking.jsonPath;

import com.alibaba.fastjson.JSONPath;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class JSONPathDemo {

    public static void main(String[] args) {
        Entity entity1 = new Entity("张三", 12);
        Entity entity2 = new Entity("李四", 13);
        Entity entity3 = new Entity("王五", 14);
        List<Entity> list = Lists.newArrayList(entity1, entity2, entity3);

        System.out.println(entity1.getAge());
        /**
         * $	根对象
         * $[-1]	最后元素
         * $[:-2]	第1个至倒数第2个
         * $[1:]	第2个之后所有元素
         * $[1,2,3]	集合中1,2,3个元素
         *
         *   Assert.assertSame(entity.getValue(), JSONPath.eval(entity, "$.value"));
         *   Assert.assertTrue(JSONPath.contains(entity, "$.value"));
         *   Assert.assertTrue(JSONPath.containsValue(entity, "$.id", 123));
         *   Assert.assertTrue(JSONPath.containsValue(entity, "$.value", entity.getValue()));
         *   Assert.assertEquals(2, JSONPath.size(entity, "$"));
         *   Assert.assertEquals(0, JSONPath.size(new Object[], "$"));
         */
        System.out.println(JSONPath.eval(entity1, "$.age"));//获取根路径的值
        System.out.println(JSONPath.contains(entity1, "$.name"));//path中是否存在对象
        System.out.println(JSONPath.containsValue(entity1, "$.age", entity2.getAge()));
        System.out.println(JSONPath.size(list, "$"));
        //获取集合中指定的元素
        List<Integer> ageList = (List<Integer>) JSONPath.eval(list, "$.age");
        System.out.println(ageList);

        //获取指定索引位置
        List<Entity> entityList = (List<Entity>)JSONPath.eval(list, "[0,2]");
        System.out.println(entityList);

        //获取指定范围内的索引
        List<Entity> entityList1 = (List<Entity>)JSONPath.eval(list, "[0:2]");
        System.out.println(entityList1);

        //获取字段在这个值里面的数
        List<Integer> entityList2 = (List<Integer>)JSONPath.eval(list, "[age in (11,14)]");
        System.out.println(entityList2);

        List<Integer> entityList3 = (List<Integer>)JSONPath.eval(list, "[age = 14]");
        System.out.println(entityList3);

        Map<String, Map<String, Map<String, String>>> stringMapMap = Collections.singletonMap("entity1", Collections.singletonMap("entity2", Collections.singletonMap("entity3", "张三")));
        System.out.println(stringMapMap);

        Collection<String> entityList4 = (Collection<String>)JSONPath.eval(stringMapMap, "$.keySet()");
        System.out.println(entityList4);
    }

}
