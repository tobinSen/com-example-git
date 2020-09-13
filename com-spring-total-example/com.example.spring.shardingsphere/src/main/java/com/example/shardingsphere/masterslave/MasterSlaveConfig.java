package com.example.shardingsphere.masterslave;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.shardingsphere.api.algorithm.masterslave.RoundRobinMasterSlaveLoadBalanceAlgorithm;
import io.shardingsphere.api.config.rule.MasterSlaveRuleConfiguration;
import io.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;
import io.shardingsphere.shardingjdbc.api.yaml.YamlMasterSlaveDataSourceFactory;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Properties;


/**
 * ShardingDataSourceFactory    -->ShardingRuleConfiguration -->TableRuleConfiguration
 * MasterSlaveDataSourceFactory -->MasterSlaveRuleConfiguration
 * <p>
 * OrchestrationShardingDataSourceFactory -->OrchestrationConfiguration -->RegistryCenterConfiguration
 */
@Configuration
@ConditionalOnExpression(value = "false ")
public class MasterSlaveConfig {
    @Bean("ds_master")
    public DataSource masterDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/ds_master");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean("ds_slave0")
    public DataSource slaveDataSource0() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/ds_slave0");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean("ds_slave1")
    public DataSource slaveDataSource1() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/ds_slave1");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    public DataSource dataSource(@Qualifier("ds_master") DataSource ds_master,
                                 @Qualifier("ds_slave0") DataSource ds_slave0,
                                 @Qualifier("ds_slave1") DataSource ds_slave1) throws SQLException {
        MasterSlaveRuleConfiguration masterSlaveRuleConfig = new MasterSlaveRuleConfiguration(
                "ds_master_slave", "ds_master", ImmutableList.of("ds_slave0", "ds_slave1"), new RoundRobinMasterSlaveLoadBalanceAlgorithm());
        return MasterSlaveDataSourceFactory.createDataSource(
                ImmutableMap.of("ds_master", ds_master, "ds_slave0", ds_slave0, "ds_slave1", ds_slave1), masterSlaveRuleConfig, Collections.emptyMap(), new Properties());
    }

    @Bean
    public DataSource dataSource() throws Exception {
        return YamlMasterSlaveDataSourceFactory.createDataSource(new ClassPathResource("classpath*:application-shardingsphere.yml").getFile());
    }
}
