package com.example.spring.dubbo.provider.provider.spi.adaptive;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.example.spring.dubbo.provider.provider.spi.adaptive.AdaptiveExt2;

/**
 * 1.@SPI 默认值
 * 2.@Adapter 无参 接口驼峰为参数名=文件中的key
 *   @Adapter 有参 参数名 = 文件key
 * 3.实现类如果标记了@Adapter优先级最高
 */
public class SPITest {

    public static void main(String[] args) {
        AdaptiveExt2 ext2 = ExtensionLoader.getExtensionLoader(AdaptiveExt2.class).getAdaptiveExtension();
        URL url = URL.valueOf("test://localhost/test?key=cloud");
        System.out.println(ext2.echo("d", url));
    }
}
