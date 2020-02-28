package com.example.spring.hutool.core;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.convert.ConverterRegistry;

public class ConvertDemo {

    public static void main(String[] args) {
        int a = 1;
        //aStr为"1"
        String aStr = Convert.toStr(a);

        Integer[] b = {1, 2, 3, 4, 5};
        //bStr为："[1, 2, 3, 4, 5]"
        String bStr = Convert.toStr(b);

        System.out.println(b.getClass().getTypeParameters()); //数组中的数据类型
        b.getClass().getGenericSuperclass();

        ConverterRegistry converterRegistry = ConverterRegistry.getInstance();
        int aq = 454553;
        String result = converterRegistry.convert(String.class, a);

    }
}
