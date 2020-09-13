package com.example.shardingsphere.transaction;

import io.shardingsphere.transaction.annotation.ShardingTransactionType;
import io.shardingsphere.transaction.api.TransactionType;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@ComponentScan("io.shardingsphere.transaction.aspect")
public class TransactionConfig {

    @ShardingTransactionType(TransactionType.XA)
    @Transactional
    public void test() {

    }


}
