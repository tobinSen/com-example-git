package com.example.spring.dubbo.provider.provider.spi.adaptive;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;

/**
 * 1.@SPI 默认值
 * 2.@Adapter 无参 接口驼峰为参数名=文件中的key
 *   @Adapter 有参 参数名 = 文件key
 * 3.实现类如果标记了@Adapter优先级最高:如果同时指定了类和方法，类优先
 *   如果多个类上面都标记了@Adapter,则会选中配置文件中第一个声明的类
 */
public class SPITest {

    public static void main(String[] args) {
        AdaptiveExt2 ext2 = ExtensionLoader.getExtensionLoader(AdaptiveExt2.class).getAdaptiveExtension();
        URL url = URL.valueOf("test://localhost/test?key=cloud");
        System.out.println(ext2.echo("d", url));
    }
}
