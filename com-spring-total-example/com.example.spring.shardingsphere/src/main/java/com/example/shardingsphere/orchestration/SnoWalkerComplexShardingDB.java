package com.example.shardingsphere.orchestration;

import com.alibaba.fastjson.JSON;
import io.shardingsphere.api.algorithm.sharding.ListShardingValue;
import io.shardingsphere.api.algorithm.sharding.ShardingValue;
import io.shardingsphere.api.algorithm.sharding.complex.ComplexKeysShardingAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class SnoWalkerComplexShardingDB implements ComplexKeysShardingAlgorithm {


    /**
     * @param availableTargetNames 可用数据源集合
     * @param shardingValues       分片键
     * @return sharding results for data sources or tables's names
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, Collection<ShardingValue> shardingValues) {

        // 0. 打印数据源集合 及 分片键属性集合
        log.info("availableTargetNames:" + JSON.toJSONString(availableTargetNames) + ",shardingValues:" + JSON.toJSONString(shardingValues));
        // availableTargetNames:["ds0","ds1","ds2","ds3"],
        // shardingValues:[{"columnName":"user_id","logicTableName":"t_new_order","values":["UD020003011903261545436593200002"]},
        //                {"columnName":"order_id","logicTableName":"t_new_order","values":["OD000000011903261545475143200001"]}]
        List<String> shardingResults = new ArrayList<>();

        // 1. 遍历分片键集合，匹配数据源
        for (ShardingValue var : shardingValues) {

            ListShardingValue<String> listShardingValue = (ListShardingValue<String>) var;
            List<String> shardingValue = (List<String>) listShardingValue.getValues();

            // shardingValue:["UD020003011903261545436593200002"]
            log.info("shardingValue:" + JSON.toJSONString(shardingValue));

            // 2. 获取数据源索引值
            String index = getIndex(listShardingValue.getLogicTableName(),
                    listShardingValue.getColumnName(),
                    shardingValue.get(0));

            // 3. 循环匹配数据源，匹配到则退出循环
            for (String name : availableTargetNames) {
                // 4. 获取逻辑数据源索引后缀，即 0，1，2，3
                String nameSuffix = name.substring(2);
                // 5. 当且仅当availableTargetNames中的数据源索引与路由值对应的分片索引相同退出循环
                if (nameSuffix.equals(index)) {
                    // 6. 添加到分片结果集合
                    shardingResults.add(name);
                    break;
                }
            }

            //匹配到一种路由规则就可以退出
            if (shardingResults.size() > 0) {
                break;
            }
        }

        return shardingResults;
    }

    /**
     * 根据分片键计算分片节点
     *
     * @param logicTableName
     * @param columnName
     * @param shardingValue
     * @return
     */
    public String getIndex(String logicTableName, String columnName, String shardingValue) {
        String index = "";
//        if (StringUtils.isEmpty(shardingValue)) {
//            throw new IllegalArgumentException("分片键值为空");
//        }
//        //截取分片键值-下标循环主键规则枚举类，匹配主键列名得到规则
//        for (DbAndTableEnum targetEnum : DbAndTableEnum.values()) {
//
//            /**目标表路由
//             * 如果逻辑表命中，判断路由键是否与列名相同
//             */
//            if (targetEnum.getTableName().equals(logicTableName)) {
//                //目标表的目标主键路由-例如：根据订单id查询订单信息
//                if (targetEnum.getShardingKey().equals(columnName)) {
//                    index = getDbIndexBySubString(targetEnum, shardingValue);
//                } else {
//                    //目标表的非目标主键路由-例如：根据内部用户id查询订单信息-内部用户id路由-固定取按照用户表库表数量
//                    //兼容且仅限根据外部id路由 查询用户信息
//                    index = getDbIndexByMod(targetEnum, shardingValue);
//                }
//                break;
//            }
//        }
//        if (StringUtils.isEmpty(index)) {
//            String msg = "从分片键值中解析数据库索引异常：logicTableName=" + logicTableName + "|columnName=" + columnName + "|shardingValue=" + shardingValue;
//            throw new IllegalArgumentException(msg);
//        }
        return index;
    }


}
