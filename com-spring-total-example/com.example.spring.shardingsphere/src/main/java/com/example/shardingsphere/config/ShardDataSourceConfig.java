package com.example.shardingsphere.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * sharding-jdbc的原理思路：4，4
 *  1、分库：找那个dataSource数据源 DataSourceRule
 *  2、分表：逻辑表-->真实表        TableRule
 *  3、分片：分片规则-->分库规则+分片规则 + 分库策略 + 分表策略  DatabaseShardingStrategy + TableShardingStrategy
 *  4、代理dataSource：ShardingDataSourceFactory.createDataSource(shardingRule());
 *
 *  查询的原理思路：3
 *   1、先看查询中是否含有分库键或分表键，如果存在其中一个键根据对应的分片算法获取实际的节点
 *   2、然后将获取所有的实际节点和指定的库或者表进行笛卡尔积
 *   3、如果没有指定任何分库键或分片键则直接返回空
 *
 *  特殊关键字的处理：3
 *  group by的规则：对每个SQL都会添加group by语句
 *  order by的规则：对每个SQL都会添加order by语句
 *  limit的规则：如果是2，2 ==》0，4对应每个表进行查询
 *
 *
 */
@Configuration
@EnableConfigurationProperties(ShardDataSourceProperties.class)
public class ShardDataSourceConfig {

    @Autowired
    private ShardDataSourceProperties shardDataSourceProperties;

    private DruidDataSource parentDs() throws SQLException {
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName(shardDataSourceProperties.getDriverClassName());
        ds.setUsername(shardDataSourceProperties.getUsername());
        ds.setUrl(shardDataSourceProperties.getUrl());
        ds.setPassword(shardDataSourceProperties.getPassword());
        ds.setFilters(shardDataSourceProperties.getFilters());
        ds.setMaxActive(shardDataSourceProperties.getMaxActive());
        ds.setInitialSize(shardDataSourceProperties.getInitialSize());
        ds.setMaxWait(shardDataSourceProperties.getMaxWait());
        ds.setMinIdle(shardDataSourceProperties.getMinIdle());
        ds.setTimeBetweenEvictionRunsMillis(shardDataSourceProperties.getTimeBetweenEvictionRunsMillis());
        ds.setMinEvictableIdleTimeMillis(shardDataSourceProperties.getMinEvictableIdleTimeMillis());
        ds.setValidationQuery(shardDataSourceProperties.getValidationQuery());
        ds.setTestWhileIdle(shardDataSourceProperties.isTestWhileIdle());
        ds.setTestOnBorrow(shardDataSourceProperties.isTestOnBorrow());
        ds.setTestOnReturn(shardDataSourceProperties.isTestOnReturn());
        ds.setPoolPreparedStatements(shardDataSourceProperties.isPoolPreparedStatements());
        ds.setMaxPoolPreparedStatementPerConnectionSize(
                shardDataSourceProperties.getMaxPoolPreparedStatementPerConnectionSize());
        ds.setRemoveAbandoned(shardDataSourceProperties.isRemoveAbandoned());
        ds.setRemoveAbandonedTimeout(shardDataSourceProperties.getRemoveAbandonedTimeout());
        ds.setLogAbandoned(shardDataSourceProperties.isLogAbandoned());
        ds.setConnectionInitSqls(shardDataSourceProperties.getConnectionInitSqls());
        return ds;
    }

    private DataSource ds0() throws SQLException {
        DruidDataSource ds = parentDs(); //公共common
        ds.setUsername(shardDataSourceProperties.getUsername0());
        ds.setUrl(shardDataSourceProperties.getUrl0());
        ds.setPassword(shardDataSourceProperties.getPassword0());
        return ds;
    }

    private DataSource ds1() throws SQLException {
        DruidDataSource ds = parentDs();//公共common
        ds.setUsername(shardDataSourceProperties.getUsername1());
        ds.setUrl(shardDataSourceProperties.getUrl1());
        ds.setPassword(shardDataSourceProperties.getPassword1());
        return ds;
    }

    //数据库的信息 -->分库本质上是在区分url上的数据库源
    private DataSourceRule dataSourceRule() throws SQLException {
        Map<String, DataSource> dataSourceMap = new HashMap<>(2);
        dataSourceMap.put("ds_0", ds0()); //逻辑库名
        dataSourceMap.put("ds_1", ds1());
        DataSourceRule dataSourceRule = new DataSourceRule(dataSourceMap);
        return dataSourceRule;
    }

    //对order对策略
    private TableRule orderTableRule() throws SQLException {
        TableRule orderTableRule = TableRule.builder("t_order") //逻辑表名
                .actualTables(Arrays.asList("t_order_0", "t_order_1"))
                .dataSourceRule(dataSourceRule()).build();
        return orderTableRule;
    }

    //分库分表策略
    private ShardingRule shardingRule() throws SQLException {
        ShardingRule shardingRule = ShardingRule.builder()
                .dataSourceRule(dataSourceRule()) //库
                .tableRules(Collections.singletonList(orderTableRule())) //表
                .databaseShardingStrategy( //库策略
                        //分片策略 = 分片键 + 分片算法
                        new DatabaseShardingStrategy("user_id", new ModuloDatabaseShardingAlgorithm()))
                .tableShardingStrategy( //表策略
                        //分片策略 = 分片键 + 分片算法
                        new TableShardingStrategy("order_id", new ModuloTableShardingAlgorithm()))
                .build();
        return shardingRule;
    }

    //代理dataSource
    @Bean
    public DataSource dataSource() throws SQLException {
        return ShardingDataSourceFactory.createDataSource(shardingRule());
    }


    //事务平台管理器
    @Bean
    public PlatformTransactionManager transactionManager() throws SQLException {
        return new DataSourceTransactionManager(dataSource());
    }
}
