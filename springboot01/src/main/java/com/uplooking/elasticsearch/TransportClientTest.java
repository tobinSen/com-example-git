package com.uplooking.elasticsearch;

import com.google.common.collect.Maps;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.util.Map;

public class TransportClientTest {

    private static String index = "elasticsearchIndex";//索引
    private static String type = "elasticsearchType";//类型

    public static void main(String[] args) throws Exception {
        //设置集群名称
        Settings settings = Settings.builder().put("cluster.name", "my-application").build();// 集群名
        //创建client
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        //1.json方式添加索引
        String jsonSource = "{\"name\":\"张三\"}";
        IndexResponse indexResponse = client.prepareIndex(index, type, "1").setSource(jsonSource).get();
        System.out.println(indexResponse.getVersion());//悲观锁，版本号进行控制

        //2.map方式添加索引
        Map<String, Object> map = Maps.newHashMap();
        map.put("name", "张三");
        map.put("age", 12);
        cl
    }
}
