package com.example.shardingsphere.config;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;
import com.google.common.collect.Range;

import java.util.Collection;
import java.util.LinkedHashSet;

//通过实现SingleKeyDatabaseShardingAlgorithm接口实现分库
public class ModuloDatabaseShardingAlgorithm implements SingleKeyDatabaseShardingAlgorithm<Integer> {

    /**
     * 分库规则 =
     *
     * @param availableTargetNames 所有数据库名称
     * @param shardingValue        分库键的值
     * @return 实际的数据库名称
     */
    @Override
    public String doEqualSharding(Collection<String> availableTargetNames, ShardingValue<Integer> shardingValue) {
        for (String each : availableTargetNames) {
            if (each.endsWith(shardingValue.getValue() % 2 + "")) {
                return each;
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * in 分库规则
     *
     * @param availableTargetNames 所有数据库名称
     * @param shardingValue        分库键的值
     * @return
     */
    @Override
    public Collection<String> doInSharding(Collection<String> availableTargetNames,
                                           ShardingValue<Integer> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());
        for (Integer value : shardingValue.getValues()) {
            for (String targetName : availableTargetNames) {
                if (targetName.endsWith(value % 2 + "")) {
                    result.add(targetName);
                }
            }
        }
        return result;
    }

    /**
     * between 分库规则
     *
     * @param availableTargetNames
     * @param shardingValue
     * @return
     */
    @Override
    public Collection<String> doBetweenSharding(Collection<String> availableTargetNames,
                                                ShardingValue<Integer> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());
        Range<Integer> range = (Range<Integer>) shardingValue.getValueRange();
        for (Integer i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
            for (String each : availableTargetNames) {
                if (each.endsWith(i % 2 + "")) {
                    result.add(each);
                }
            }
        }
        return result;
    }


}
