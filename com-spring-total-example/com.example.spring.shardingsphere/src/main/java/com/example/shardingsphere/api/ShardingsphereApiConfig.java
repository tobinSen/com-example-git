package com.example.shardingsphere.api;

import io.shardingsphere.api.config.rule.ShardingRuleConfiguration;
import io.shardingsphere.api.config.rule.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.InlineShardingStrategyConfiguration;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import io.shardingsphere.shardingjdbc.api.yaml.YamlShardingDataSourceFactory;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * maven:
 * <dependency>
 * <groupId>io.shardingsphere</groupId>
 * <artifactId>sharding-jdbc-core</artifactId>
 * <version>3.1.0</version>
 * </dependency>
 */
@Configuration
@ConditionalOnExpression(value = "1!=1 ")
public class ShardingsphereApiConfig {


    @Bean("ds_0")
    public DataSource dataSource0() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/ds0");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean("ds_1")
    public DataSource dataSource1() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/ds1");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        return dataSource;
    }

    // 4点
    @Bean
    public ShardingRuleConfiguration shardingRuleConfiguration() {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration();
        //逻辑表
        orderTableRuleConfig.setLogicTable("t_order");
        //真实节点
        orderTableRuleConfig.setActualDataNodes("ds${0..1}.t_order${0..1}"); //这里需要含有ds+tb 可以看出有几种分配方式
        //分库策略
        orderTableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "ds${user_id % 2}"));
        //分表策略
        orderTableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "t_order${order_id % 2}"));
        shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);
        return shardingRuleConfig;
    }

    @Bean
    public DataSource dataSource(@Qualifier("ds_0") DataSource ds_0, @Qualifier("ds_1") DataSource ds_1,
                                 ShardingRuleConfiguration shardingRuleConfiguration) throws SQLException {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("ds0", ds_0);
        dataSourceMap.put("ds1", ds_1);
        //产生一个代理的dataSource
        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfiguration, Collections.emptyMap(), new Properties());
    }

    //基于yaml来配置代理dataSource
    @Bean
    public DataSource yamlDataSource() throws IOException, SQLException {
        return YamlShardingDataSourceFactory.createDataSource(new ClassPathResource("classpath*:application-shardingsphere.yml").getFile());
    }
}
