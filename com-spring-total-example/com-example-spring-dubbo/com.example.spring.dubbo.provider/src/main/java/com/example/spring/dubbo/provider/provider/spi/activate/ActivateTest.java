package com.example.spring.dubbo.provider.provider.spi.activate;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;

import java.util.List;

public class ActivateTest {

    public static void main(String[] args) {
        ExtensionLoader<ActivateExt1> loader = ExtensionLoader.getExtensionLoader(ActivateExt1.class);
        URL url = URL.valueOf("test://localhost/test");
        //查询组为default_group的ActivateExt1的实现
        List<ActivateExt1> list = loader.getActivateExtension(url, new String[]{"value"}, "value");
        System.out.println(list.size());
        System.out.println(list.get(0).echo("null"));
    }
}
