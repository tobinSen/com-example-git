package com.example.hadoop.hbase.client;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.junit.Test;

public class HBaseTest {

    private static Configuration configuration;

    static {
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "localhost");
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
    }

    @Test
    public void isTableExist() throws Exception {
        //Connection connection = ConnectionFactory.createConnection(configuration);
        //Admin admin = connection.getAdmin();
        HBaseAdmin admin = new HBaseAdmin(configuration);
        boolean isTableExists = admin.tableExists("student");
        System.out.println(isTableExists);
    }
}
