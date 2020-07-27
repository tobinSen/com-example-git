package com.example.shardingsphere.orchestration;

import com.google.common.collect.ImmutableMap;
import io.shardingsphere.api.config.rule.ShardingRuleConfiguration;
import io.shardingsphere.api.config.rule.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.InlineShardingStrategyConfiguration;
import io.shardingsphere.orchestration.config.OrchestrationConfiguration;
import io.shardingsphere.orchestration.reg.api.RegistryCenterConfiguration;
import io.shardingsphere.shardingjdbc.orchestration.api.OrchestrationShardingDataSourceFactory;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Properties;

@Configuration
public class OrchestrationApiConfig {

    @Bean("osc_ds0")
    public DataSource dataSource0() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/osc_ds0");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean("osc_ds1")
    public DataSource dataSource1() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/osc_ds1");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        return dataSource;
    }

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
    public RegistryCenterConfiguration registryCenterConfiguration() {
        RegistryCenterConfiguration regConfig = new RegistryCenterConfiguration();
        regConfig.setServerLists("localhost:2181");
        regConfig.setNamespace("sharding-sphere-orchestration"); //注册中心配置
        return regConfig;
    }

    @Bean
    public OrchestrationConfiguration orchestrationConfiguration(RegistryCenterConfiguration regConfig) {
        return new OrchestrationConfiguration("orchestration-sharding-data-source", regConfig, false);
    }

    @Bean
    public DataSource dataSource(@Qualifier("osc_ds0") DataSource osc_ds0,
                                 @Qualifier("osc_ds1") DataSource osc_ds1,
                                 OrchestrationConfiguration orchConfig, ShardingRuleConfiguration shardingRuleConfig) throws Exception {
        return OrchestrationShardingDataSourceFactory.createDataSource(ImmutableMap.of("osc_ds0", osc_ds0, "osc_ds1", osc_ds1), shardingRuleConfig, Collections.emptyMap(), new Properties(), orchConfig);
    }
}

