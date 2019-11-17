package com.example.spring.springcloud.configclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope  //动态刷新
public class ConfigClientController {

    @Value("${name}")
    private String name;
    @Value("${age}")
    private Integer age;
    @Value("${like}")
    private String like;

    /**
     * spring-config并没有动态更新配置的功能，只有重启后才会进行刷新
     * <p>
     * 在config-clinet的pom.xml中新增spring-boot-starter-actuator监控模块，其中包含了/refresh刷新API。
     */

    @RequestMapping("client.do")
    public void test() {
        System.out.println(name + age + like);
    }
}
