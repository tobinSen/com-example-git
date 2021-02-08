package com.example.spring.rocketmq.producer.producer;

import com.example.spring.rocketmq.producer.listener.TransactionListenerImpl;
import io.openmessaging.MessagingAccessPoint;
import io.openmessaging.OMS;
import io.openmessaging.OMSBuiltinKeys;
import io.openmessaging.consumer.PullConsumer;
import io.openmessaging.consumer.PushConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;


/**
 * MQAdmin
 * |
 * ClientConfig     MQProducer
 * |
 * DefaultMQProducer ->DefaultMQProducerImpl
 * ServiceState
 */

public class TotalMQProducer {

    //-------------------------producer------------------------------
    //同步消息
    public static class SyncProducer {
        public static void main(String[] args) throws Exception {
            // 实例化消息生产者Producer
            DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
            producer.setProducerGroup("product-group");
            producer.setInstanceName("product-instance"); //一个生产者组一个实例
            // 设置NameServer的地址
            producer.setNamesrvAddr("localhost:9876");
            // 启动Producer实例
            producer.start();
            for (int i = 0; i < 100; i++) {
                // 创建消息，并指定Topic，Tag和消息体
                Message msg = new Message("TopicTest" /* Topic */,
                        "TagA" /* Tag */,
                        ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
                );
                // 发送消息到一个Broker
                SendResult sendResult = producer.send(msg);
                // 通过sendResult返回消息是否成功送达
                System.out.printf("%s%n", sendResult);
            }
            // 如果不再发送消息，关闭Producer实例。
            producer.shutdown();
        }
    }

    //异步消息
    public static class AsyncProducer {
        public static void main(String[] args) throws Exception {
            // 实例化消息生产者Producer
            DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
            // 设置NameServer的地址
            producer.setNamesrvAddr("localhost:9876");
            // 启动Producer实例
            producer.start();
            producer.setRetryTimesWhenSendAsyncFailed(0);
            for (int i = 0; i < 100; i++) {
                final int index = i;
                // 创建消息，并指定Topic，Tag和消息体
                Message msg = new Message("TopicTest",
                        "TagA",
                        "OrderID188",
                        "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
                // SendCallback接收异步返回结果的回调
                producer.send(msg, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        System.out.printf("%-10d OK %s %n", index,
                                sendResult.getMsgId());
                    }

                    @Override
                    public void onException(Throwable e) {
                        System.out.printf("%-10d Exception %s %n", index, e);
                        e.printStackTrace();
                    }
                });
            }
            // 如果不再发送消息，关闭Producer实例。
            producer.shutdown();
        }
    }

    //单向发送
    public static class OneWayProducer {
        public static void main(String[] args) throws Exception {
            // 实例化消息生产者Producer
            DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
            // 设置NameServer的地址
            producer.setNamesrvAddr("localhost:9876");
            // 启动Producer实例
            producer.start();
            for (int i = 0; i < 100; i++) {
                // 创建消息，并指定Topic，Tag和消息体
                Message msg = new Message("TopicTest" /* Topic */,
                        "TagA" /* Tag */,
                        ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
                );
                // 发送单向消息，没有任何返回结果
                producer.sendOneway(msg);

            }
            // 如果不再发送消息，关闭Producer实例。
            producer.shutdown();
        }
    }

    /**
     * Producer，发送顺序消息
     */
    public static class Producer {

        public static void main(String[] args) throws Exception {
            DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");

            producer.setNamesrvAddr("127.0.0.1:9876");

            producer.start();

            String[] tags = new String[]{"TagA", "TagC", "TagD"};

            // 订单列表
            List<OrderStep> orderList = new Producer().buildOrders();

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = sdf.format(date);
            for (int i = 0; i < 10; i++) {
                // 加个时间前缀
                String body = dateStr + " Hello RocketMQ " + orderList.get(i);
                Message msg = new Message("TopicTest", tags[i % tags.length], "KEY" + i, body.getBytes());
                //msg.putUserProperty("user", "user");
                SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                    @Override
                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                        Long id = (Long) arg;  //根据订单id选择发送queue
                        long index = id % mqs.size();
                        return mqs.get((int) index);
                    }
                }, orderList.get(i).getOrderId());//订单id

                System.out.println(String.format("SendResult status:%s, queueId:%d, body:%s",
                        sendResult.getSendStatus(),
                        sendResult.getMessageQueue().getQueueId(),
                        body));
            }

            producer.shutdown();
        }

        /**
         * 订单的步骤
         */
        private static class OrderStep {
            private long orderId;
            private String desc;

            public long getOrderId() {
                return orderId;
            }

            public void setOrderId(long orderId) {
                this.orderId = orderId;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            @Override
            public String toString() {
                return "OrderStep{" +
                        "orderId=" + orderId +
                        ", desc='" + desc + '\'' +
                        '}';
            }
        }

        /**
         * 生成模拟订单数据
         */
        private List<OrderStep> buildOrders() {
            List<OrderStep> orderList = new ArrayList<OrderStep>();

            OrderStep orderDemo = new OrderStep();
            orderDemo.setOrderId(15103111039L);
            orderDemo.setDesc("创建");
            orderList.add(orderDemo);

            orderDemo = new OrderStep();
            orderDemo.setOrderId(15103111065L);
            orderDemo.setDesc("创建");
            orderList.add(orderDemo);

            orderDemo = new OrderStep();
            orderDemo.setOrderId(15103111039L);
            orderDemo.setDesc("付款");
            orderList.add(orderDemo);

            orderDemo = new OrderStep();
            orderDemo.setOrderId(15103117235L);
            orderDemo.setDesc("创建");
            orderList.add(orderDemo);

            orderDemo = new OrderStep();
            orderDemo.setOrderId(15103111065L);
            orderDemo.setDesc("付款");
            orderList.add(orderDemo);

            orderDemo = new OrderStep();
            orderDemo.setOrderId(15103117235L);
            orderDemo.setDesc("付款");
            orderList.add(orderDemo);

            orderDemo = new OrderStep();
            orderDemo.setOrderId(15103111065L);
            orderDemo.setDesc("完成");
            orderList.add(orderDemo);

            orderDemo = new OrderStep();
            orderDemo.setOrderId(15103111039L);
            orderDemo.setDesc("推送");
            orderList.add(orderDemo);

            orderDemo = new OrderStep();
            orderDemo.setOrderId(15103117235L);
            orderDemo.setDesc("完成");
            orderList.add(orderDemo);

            orderDemo = new OrderStep();
            orderDemo.setOrderId(15103111039L);
            orderDemo.setDesc("完成");
            orderList.add(orderDemo);

            return orderList;
        }
    }

    //延时发送消息
    public static class ScheduledMessageProducer {
        public static void main(String[] args) throws Exception {
            // 实例化一个生产者来产生延时消息
            DefaultMQProducer producer = new DefaultMQProducer("ExampleProducerGroup");
            // 启动生产者
            producer.start();
            int totalMessagesToSend = 100;
            for (int i = 0; i < totalMessagesToSend; i++) {
                Message message = new Message("TestTopic", ("Hello scheduled message " + i).getBytes());
                // 设置延时等级3,这个消息将在10s之后发送(现在只支持固定的几个时间,详看delayTimeLevel)
                message.setDelayTimeLevel(3);
                // 发送消息
                producer.send(message);
            }
            // 关闭生产者
            producer.shutdown();
        }
    }

    //通过sql92筛选
    public static class producerSQL92 {
        public static void main(String[] args) throws Exception {
            DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
            producer.start();
            for (int i = 0; i < 10; i++) {
                Message msg = new Message("TopicTest",
                        "tag" + i,
                        ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
                );
                // 设置一些属性
                msg.putUserProperty("a", String.valueOf(i));
                SendResult sendResult = producer.send(msg);
            }

            producer.shutdown();
        }
    }

    /**
     * 事务消息共有三种状态，提交状态、回滚状态、中间状态：
     * <p>
     * TransactionStatus.CommitTransaction: 提交事务，它允许消费者消费此消息。
     * TransactionStatus.RollbackTransaction: 回滚事务，它代表该消息将被删除，不允许被消费。
     * TransactionStatus.Unknown: 中间状态，它代表需要检查消息队列来确定状态。
     */
    public static class TransactionProducer {
        public static void main(String[] args) throws MQClientException, InterruptedException {
            TransactionListener transactionListener = new TransactionListenerImpl();
            TransactionMQProducer producer = new TransactionMQProducer("please_rename_unique_group_name");
            ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setName("client-transaction-msg-check-thread");
                    return thread;
                }
            });
            producer.setExecutorService(executorService);
            producer.setTransactionListener(transactionListener);
            producer.start();
            String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
            for (int i = 0; i < 10; i++) {
                try {
                    Message msg =
                            new Message("TopicTest1234", tags[i % tags.length], "KEY" + i,
                                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                    SendResult sendResult = producer.sendMessageInTransaction(msg, null);
                    System.out.printf("%s%n", sendResult);
                    Thread.sleep(10);
                } catch (MQClientException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < 100000; i++) {
                Thread.sleep(1000);
            }
            producer.shutdown();
        }
    }

    //openMessage 消息流式处理
    public static class SimpleProducer {
        public static void main(String[] args) throws Exception {
            final MessagingAccessPoint messagingAccessPoint =
                    OMS.getMessagingAccessPoint("oms:rocketmq://localhost:9876/default:default");
            final io.openmessaging.producer.Producer producer = messagingAccessPoint.createProducer();
            messagingAccessPoint.startup();
            System.out.printf("MessagingAccessPoint startup OK%n");
            producer.startup();
            System.out.printf("Producer startup OK%n");
            {
                io.openmessaging.Message message = producer.createBytesMessage("OMS_HELLO_TOPIC", "OMS_HELLO_BODY".getBytes(Charset.forName("UTF-8")));
                io.openmessaging.producer.SendResult sendResult = producer.send(message);
                //final Void aVoid = result.get(3000L);
                System.out.printf("Send async message OK, msgId: %s%n", sendResult.messageId());
            }
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            {
                io.openmessaging.Future<io.openmessaging.producer.SendResult> result = producer.sendAsync(producer.createBytesMessage("OMS_HELLO_TOPIC", "OMS_HELLO_BODY".getBytes(Charset.forName("UTF-8"))));
                result.addListener(new io.openmessaging.FutureListener<io.openmessaging.producer.SendResult>() {
                    @Override
                    public void operationComplete(io.openmessaging.Future<io.openmessaging.producer.SendResult> future) {
                        if (future.getThrowable() != null) {
                            System.out.printf("Send async message Failed, error: %s%n", future.getThrowable().getMessage());
                        } else {
                            System.out.printf("Send async message OK, msgId: %s%n", future.get().messageId());
                        }
                        countDownLatch.countDown();
                    }
                });
            }
            {
                producer.sendOneway(producer.createBytesMessage("OMS_HELLO_TOPIC", "OMS_HELLO_BODY".getBytes(Charset.forName("UTF-8"))));
                System.out.printf("Send oneway message OK%n");
            }
            try {
                countDownLatch.await();
                Thread.sleep(500); // 等一些时间来发送消息
            } catch (InterruptedException ignore) {
            }
            producer.shutdown();
        }
    }

    //------------------------------------consumer------------------------------------------

    public static class Consumer {

        public static void main(String[] args) throws InterruptedException, MQClientException {

            // 实例化消费者
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name");

            // 设置NameServer的地址
            consumer.setNamesrvAddr("localhost:9876");

            // 订阅一个或者多个Topic，以及Tag来过滤需要消费的消息
            consumer.subscribe("TopicTest", "*");
            // 注册回调实现类来处理从broker拉取回来的消息
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                    // 标记该消息已经被成功消费
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            // 启动消费者实例
            consumer.start();
            System.out.printf("Consumer Started.%n");
        }
    }

    /**
     * 顺序消息消费，带事务方式（应用可控制Offset什么时候提交）
     */
    public static class ConsumerInOrder {

        public static void main(String[] args) throws Exception {
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name_3");
            consumer.setNamesrvAddr("127.0.0.1:9876");
            /**
             * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
             * 如果非第一次启动，那么按照上次消费的位置继续消费
             */
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

            consumer.subscribe("TopicTest", "TagA || TagC || TagD");

            consumer.registerMessageListener(new MessageListenerOrderly() {

                Random random = new Random();

                //保证的是队列中的消息的顺序性，而消费者需要保证一个队列由一个消费者进行消费，消费的顺序性了
                @Override
                public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgS, ConsumeOrderlyContext context) {
                    //设置自动提交,如果不设置自动提交就算返回SUCCESS,消费者关闭重启 还是会重复消费的
                    context.setAutoCommit(true);
                    for (MessageExt msg : msgS) {
                        // 可以看到每个queue有唯一的consume线程来消费, 订单对每个queue(分区)有序
                        System.out.println("consumeThread=" + Thread.currentThread().getName() + "queueId=" + msg.getQueueId() + ", content:" + new String(msg.getBody()));
                    }

                    try {
                        //模拟业务逻辑处理中...
                        TimeUnit.SECONDS.sleep(random.nextInt(10));
                    } catch (Exception e) {
                        e.printStackTrace();
                        //如果出现异常,消费失败，挂起消费队列一会会，稍后继续消费
                        return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                    }
                    return ConsumeOrderlyStatus.SUCCESS;
                }
            });

            consumer.start();

            System.out.println("Consumer Started.");
        }
    }

    //延时消费
    public static class ScheduledMessageConsumer {
        public static void main(String[] args) throws Exception {
            // 实例化消费者
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ExampleConsumer");
            // 订阅Topics
            consumer.subscribe("TestTopic", "*");
            // 注册消息监听者
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messages, ConsumeConcurrentlyContext context) {
                    for (MessageExt message : messages) {
                        // Print approximate delay time period
                        System.out.println("Receive message[msgId=" + message.getMsgId() + "] " + (System.currentTimeMillis() - message.getStoreTimestamp()) + "ms later");
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            // 启动消费者
            consumer.start();
        }
    }

    /**
     * ### 5.1 基本语法
     * <p>
     * RocketMQ只定义了一些基本语法来支持这个特性。你也可以很容易地扩展它。
     * <p>
     * - 数值比较，比如：**>，>=，<，<=，BETWEEN，=；**
     * - 字符比较，比如：**=，<>，IN；**
     * - **IS NULL** 或者 **IS NOT NULL；**
     * - 逻辑符号 **AND，OR，NOT；**
     * <p>
     * 常量支持类型为：
     * <p>
     * - 数值，比如：**123，3.1415；**
     * - 字符，比如：**'abc'，必须用单引号包裹起来；**
     * - **NULL**，特殊的常量
     * - 布尔值，**TRUE** 或 **FALSE**
     * <p>
     * 只有使用push模式的消费者才能用使用SQL92标准的sql语句，接口如下：
     */
    //筛选SQL92
    public static class ConsumerSQL92 {
        public static void main(String[] args) throws Exception {
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name_4");
            // 只有订阅的消息有这个属性a, a >=0 and a <= 3
            // msg.putUserProperty("a", String.valueOf(i)); 对应
            consumer.subscribe("TopicTest", MessageSelector.bySql("a between 0 and 3"));
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
        }
    }

    //openMessage consumer pull方式
    public static class SimplePullConsumer {
        public static void main(String[] args) {
            final MessagingAccessPoint messagingAccessPoint =
                    OMS.getMessagingAccessPoint("oms:rocketmq://localhost:9876/default:default");
            messagingAccessPoint.startup();
            final io.openmessaging.producer.Producer producer = messagingAccessPoint.createProducer();
            final PullConsumer consumer = messagingAccessPoint.createPullConsumer(
                    OMS.newKeyValue().put(OMSBuiltinKeys.CONSUMER_ID, "OMS_CONSUMER"));
            messagingAccessPoint.startup();
            System.out.printf("MessagingAccessPoint startup OK%n");
            final String queueName = "TopicTest";
            producer.startup();
            io.openmessaging.Message msg = producer.createBytesMessage(queueName, "Hello Open Messaging".getBytes());
            io.openmessaging.producer.SendResult sendResult = producer.send(msg);
            System.out.printf("Send Message OK. MsgId: %s%n", sendResult.messageId());
            producer.shutdown();
            consumer.attachQueue(queueName);
            consumer.startup();
            System.out.printf("Consumer startup OK%n");
            // 运行直到发现一个消息被发送了
            boolean stop = false;
            while (!stop) {
                io.openmessaging.Message message = consumer.receive();
                if (message != null) {
                    String msgId = message.sysHeaders().getString(io.openmessaging.Message.BuiltinKeys.MESSAGE_ID);
                    System.out.printf("Received one message: %s%n", msgId);
                    consumer.ack(msgId);
                    if (!stop) {
                        stop = msgId.equalsIgnoreCase(sendResult.messageId());
                    }
                } else {
                    System.out.printf("Return without any message%n");
                }
            }
            consumer.shutdown();
            messagingAccessPoint.shutdown();
        }
    }

    //openMessage consumer push方式
    public static class SimplePushConsumer {
        public static void main(String[] args) {
            final MessagingAccessPoint messagingAccessPoint = OMS
                    .getMessagingAccessPoint("oms:rocketmq://localhost:9876/default:default");
            final PushConsumer consumer = messagingAccessPoint.
                    createPushConsumer(OMS.newKeyValue().put(OMSBuiltinKeys.CONSUMER_ID, "OMS_CONSUMER"));
            messagingAccessPoint.startup();
            System.out.printf("MessagingAccessPoint startup OK%n");
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    consumer.shutdown();
                    messagingAccessPoint.shutdown();
                }
            }));
            consumer.attachQueue("OMS_HELLO_TOPIC", new io.openmessaging.consumer.MessageListener() {
                @Override
                public void onReceived(io.openmessaging.Message message, Context context) {
                    System.out.printf("Received one message: %s%n", message.sysHeaders().getString(io.openmessaging.Message.BuiltinKeys.MESSAGE_ID));
                    context.ack();
                }
            });
            consumer.startup();
            System.out.printf("Consumer startup OK%n");
        }
    }
}
