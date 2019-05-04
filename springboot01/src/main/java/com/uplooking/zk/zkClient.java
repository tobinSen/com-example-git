package com.uplooking.zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;

public class zkClient {

    private static String connectString = "hadoop102:2181,hadoop:2181";

    private static int sessionTimeout = 200;

    private static ZooKeeper zkClient;

    public static void main(String[] args) throws Exception {
        zkClient = new ZooKeeper(connectString, sessionTimeout, (watchedEvent) -> {
            //监听回调
            try {
                List<String> children = zkClient.getChildren("/at", true);


            } catch (KeeperException e) {
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
    }

    public void createNode() throws Exception {
        //创建节点
        String path = zkClient.create("/atGuiGu", "张三".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        //获取子节点并监听数据变化
        List<String> children = zkClient.getChildren("/atGuiGu", true);
    }

    public void distributeServer() throws Exception {
        //1、连接zk集群
        //2、
    }
}
