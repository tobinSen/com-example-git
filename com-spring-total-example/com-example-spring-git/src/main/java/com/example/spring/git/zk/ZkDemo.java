//package com.example.spring.git.zk;
//
//import org.apache.curator.framework.CuratorFramework;
//import org.apache.curator.framework.CuratorFrameworkFactory;
//import org.apache.curator.framework.api.BackgroundCallback;
//import org.apache.curator.framework.api.CuratorEvent;
//import org.apache.curator.framework.recipes.cache.*;
//import org.apache.curator.framework.recipes.locks.InterProcessLock;
//import org.apache.curator.framework.recipes.locks.InterProcessMutex;
//import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
//import org.apache.curator.framework.state.ConnectionState;
//import org.apache.curator.framework.state.ConnectionStateListener;
//import org.apache.curator.retry.RetryOneTime;
//import org.apache.curator.utils.CloseableUtils;
//import org.apache.zookeeper.CreateMode;
//import org.apache.zookeeper.ZooDefs;
//import org.apache.zookeeper.data.Stat;
//
//import java.util.List;
//
//public class ZkDemo {
//    public static void main(String[] args) throws Exception {
//        //创建连接对象
//        CuratorFramework client = CuratorFrameworkFactory.builder()
//                //IP地址端口
//                .connectString("127.0.0.1:2181")
//                //会话超时
//                .sessionTimeoutMs(5000)
//                //重连机制(会话超时之后3秒，一次重连)
//                .retryPolicy(new RetryOneTime(3000))
//                //命名空间
//                .namespace("curator")
//                .build();
//
//        //添加curator的状态监听
//        client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
//            @Override
//            public void stateChanged(CuratorFramework client, ConnectionState newState) {
//
//            }
//        });
//
//        //打开连接
//        client.start();
//        System.out.println(client.isStarted());
//
//        //1.创建节点
//        String s = client.create()
//                //如果父节点不存在，就传进父节点
//                .creatingParentContainersIfNeeded()
//                //节点类型
//                .withMode(CreateMode.PERSISTENT)
//                //节点权限的列表
//                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
//                //异步方式创建节点
//                .inBackground(new BackgroundCallback() {
//                    @Override
//                    public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
//                        System.out.println(event.getPath());
//                        System.out.println(event.getType());
//                    }
//                })
//                .forPath("/node2/subNode", "node4".getBytes());
//        System.out.println(s);
//
//        //2.更新节点
//        client.setData().forPath("/node3","node3".getBytes());
//
//        //3.删除节点
//        client.delete().deletingChildrenIfNeeded().forPath("/node3");
//
//        //4.获取节点
//        List<String> list = client.getChildren().forPath("/");
//        byte[] bytes = client.getData().forPath("/node2");
//        System.out.println(new String(bytes));
//
//        //将属性存储到Stat对象中
//        client.getData().storingStatIn(new Stat())
//                .forPath("/node2/subNode");
//
//
//        //curator提供了两种watcher(Cache)来监听节点的变化
//        /**
//         * Node Cache:只是监听某一个特定节点，监听节点的新增和修改
//         * PathChildren Cache:监听一个ZNode的子节点，当一个子节点增加，更新，删除时，Path Cache会改变它的状态，会包含最新的子节点，子节点数据和状态
//         */
//
//        NodeCache nodeCache = new NodeCache(client, "/node2");
//        nodeCache.start();
//        nodeCache.getListenable().addListener(new NodeCacheListener() {
//            @Override
//            public void nodeChanged() throws Exception {
//                System.out.println(nodeCache.getCurrentData().getPath());
//            }
//        });
//        nodeCache.close();
//
//        PathChildrenCache pathChildrenCache = new PathChildrenCache(client, "node2", true);
//        pathChildrenCache.start();
//        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
//            @Override
//            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
//
//            }
//        });
//        pathChildrenCache.close();
//
//        //事务
//
//        client.inTransaction()
//                .create().forPath("/node1","node1".getBytes())
//                .and()
//                .setData().forPath("/node2")
//                .and()
//                .commit();
//
//        /**
//         * 分布式锁
//         *    InterProcessMutex: 分布式可重入排它锁
//         *    InterProcessReadWriteLock: 分布式读写锁
//         */
//
//        InterProcessLock interProcessLock = new InterProcessMutex(client, "/lock1");
//        //获取锁
//        interProcessLock.acquire();
//        //释放锁
//        interProcessLock.release();
//
//
//        InterProcessReadWriteLock interProcessReadWriteLock = new InterProcessReadWriteLock(client, "/lock1");
//        interProcessLock.acquire();
//        interProcessLock.release();
//
//        //关闭连接
//        CloseableUtils.closeQuietly(client);
//
//    }
//}
