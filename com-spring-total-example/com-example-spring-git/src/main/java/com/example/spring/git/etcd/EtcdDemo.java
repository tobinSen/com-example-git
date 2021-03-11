package com.example.spring.git.etcd;

import io.etcd.jetcd.*;
import io.etcd.jetcd.lease.LeaseGrantResponse;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;

public class EtcdDemo {


    public static void main(String[] args) {

        // create client
        Client client = Client.builder().endpoints("http://127.0.0.1:2379").build();
        // 1.etcd锁
        Lock lockClient = client.getLockClient();
        // 2.租约
        Lease leaseClient = client.getLeaseClient();

        KV kvClient = client.getKVClient();
        Watch watchClient = client.getWatchClient();

        //name-锁标识
        ByteSequence name = ByteSequence.from("mutex1".getBytes());
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    //创建一个租约
                    LeaseGrantResponse ttl = leaseClient.grant(30).get();
                    //自动续约-防止ttl超时线程未执行完
                    leaseClient.keepAlive(ttl.getID(), new StreamObserver() {
                        @Override
                        public void onNext(Object value) {
                            //执行续约之后的回调
                            System.out.println("执行续约...ID: {}, ttl: {}" + ttl.getID() + ttl.getTTL());

                        }

                        @Override
                        public void onError(Throwable t) {
                            //异常，就会停止续约，比如调用revoke取消续约，租约不存在
                        }

                        @Override
                        public void onCompleted() {
                        }
                    });
                    //尝试获取锁
                    ByteSequence key = lockClient.lock(name, ttl.getID()).get().getKey();
                    long start = System.currentTimeMillis();
                    //模拟线程执行时长
                    TimeUnit.SECONDS.sleep(3);
                    //取消租约
                    leaseClient.revoke(ttl.getID());
                    lockClient.unlock(key);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "线程" + i).start();
        }

    }
}
