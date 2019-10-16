package com.example.hadoop.hbase.client;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * DDL:
 * 1.判断表是否存在
 * 2.创建表
 * 3.创建命名空间
 * 4.删除表
 * <p>
 * <p>
 * DML:
 * 5.插入数据
 * 6.查数据(get)
 * 7.查数据(scan)
 * 8.删除数据
 */

public class HBaseTest {

    private static Connection connection = null;
    private static Admin admin = null;

    static {

        //1.HBase配置信息
        //HBaseConfiguration configuration = new HBaseConfiguration();
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "localhost");

        //2.创建admin对象
        //HBaseAdmin admin = new HBaseAdmin(configuration);
        try {
            connection = ConnectionFactory.createConnection(configuration);
            admin = connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close() throws Exception {
        if (null != admin) admin.close();
        if (null != connection) connection.close();
    }

    //判断表是否存在
    public static boolean isTableExist(String tableName) throws Exception {
        //3.判断表是否存储
        boolean exists = admin.tableExists(TableName.valueOf(tableName));
        //4.关闭连接
        //close();
        return exists;
    }

    //创建表
    public static void create(String tableName, String... cfs) throws Exception {
        if (cfs.length <= 0) {
            System.out.println("请设置列族信息");
            return;
        }
        if (isTableExist(tableName)) {
            return;
        }
        HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
        for (String cf : cfs) {
            HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(cf);
            hColumnDescriptor.setMaxVersions(10);

            hTableDescriptor.addFamily(hColumnDescriptor);
        }

        admin.createTable(hTableDescriptor);
        close();
    }

    //删除表
    public static void drop(String tableName) throws Exception {
        //1.表下线
        admin.disableTable(TableName.valueOf(tableName));
        //2.表删除
        admin.deleteTable(TableName.valueOf(tableName));
    }

    //传进命名空间
    public static void createNamespace(String namespace) throws Exception {
        NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create(namespace).build();
        admin.createNamespace(namespaceDescriptor);

    }

    //表中插入数据
    public static void put(String tableName, String rowKey, String cf, String cn, String value) throws Exception {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Put put = new Put(Bytes.toBytes(rowKey));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes(cn), Bytes.toBytes(value));
        table.put(put);
        table.close();
    }

    //表中获取数据
    public static void get(String tableName, String rowKey) throws Exception {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Get get = new Get(Bytes.toBytes(rowKey));
        Result result = table.get(get);
        for (Cell cell : result.rawCells()) {
            String family = Bytes.toString(CellUtil.cloneFamily(cell));
            String cl = Bytes.toString(CellUtil.cloneQualifier(cell));
            String value = Bytes.toString(CellUtil.cloneValue(cell));
            String row = Bytes.toString(CellUtil.cloneRow(cell));

            System.out.println(family);
            System.out.println(cl);
            System.out.println(value);

            System.out.println(row);
        }
        table.close();
    }

    public static void main(String[] args) throws Exception {

        //1.判断表是否存储
        //System.out.println("判断表是否存储:" + isTableExist("student")); //true
        //2.创建一张表
        //create("teacher", "info", "infoItem");
        //drop("teacher");
        //createNamespace("hbaseNamespace");
        //put("student", "1003", "info", "name", "zhangsan");
        get("student", "1003");
    }
}
