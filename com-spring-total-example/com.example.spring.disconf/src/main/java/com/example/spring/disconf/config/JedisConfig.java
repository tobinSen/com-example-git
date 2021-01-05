package com.example.spring.disconf.config;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import com.baidu.disconf.client.common.annotations.DisconfUpdateService;
import com.baidu.disconf.client.common.update.IDisconfUpdate;
import org.springframework.context.annotation.Configuration;

@Configuration
@DisconfFile(filename = "redis.properties")
@DisconfUpdateService(classes = { JedisConfig.class }) // 这里或者写成 @DisconfUpdateService(confFileKeys = { "redis.properties" })
public class JedisConfig  implements IDisconfUpdate {

    // 代表连接地址
    private String host;

    // 代表连接port
    private int port;

    /**
     * 地址, 分布式文件配置
     *
     * @return
     */
    @DisconfFileItem(name = "redis.host", associateField = "host")
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    /**
     * 端口, 分布式文件配置
     *
     * @return
     */
    @DisconfFileItem(name = "redis.port", associateField = "port")
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    //当这个配置类进行同步更新的时候会触发一个回调操作
    @Override
    public void reload() throws Exception {
        //这里是回调的逻辑。。。
    }
}
