package com.uplooking.configuration;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;

@Configuration
@EnableElasticsearchRepositories("com.uplooking.repository")
public class EsConfiguration {

    // 假设使用三个node,(一主两备)的配置。在实际的生产环境，需在properties文件中替换成实际ip(内网或者外网ip)
    @Value("${elasticsearch.host1}")
    private String esHost;// master node

    @Value("${elasticsearch.host2:}")
    private String esHost2;//replica node

    @Value("${elasticsearch.host3:}")
    private String esHost3;//replica node

    @Value("${elasticsearch.port}")
    private int esPort;

    @Value("${elasticsearch.clustername}")
    private String esClusterName;

    @Bean
    public TransportClient transportClient() throws Exception {

        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", esClusterName)
                .build();

        TransportClient transportClient = TransportClient.builder()
                .settings(settings)
                .build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(esHost), esPort));
        if (StringUtils.isNotEmpty(esHost2)) {
            transportClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(esHost2), esPort));
        }
        if (StringUtils.isNotEmpty(esHost3)) {
            transportClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(esHost3), esPort));
        }
        return transportClient;
    }

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate() throws Exception {
        return new ElasticsearchTemplate(transportClient());
    }

}
