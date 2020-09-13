package com.example.spring.mpush;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.concurrent.ScheduledExecutorService;

public class MQTTServerDemo {

    public static final String HOST = "tcp://localhost:1883";
    public static final String TOPIC = "root/topic/123";
    public static final String clientid = "client11";

    /**
     * 启动入口     *     * @param args     * @throws MqttException
     */
    public static void main(String[] args) throws MqttException {
        ServerMqtt server = new ServerMqtt();
        server.message = new MqttMessage();
        server.message.setQos(1);
        server.message.setRetained(true);
        server.message.setPayload(("hello，MQTT").getBytes());
        server.client.publish(server.topic11.getName(), server.message);
        System.out.println(server.message.isRetained() + "------ratained状态");

    }

    static class ServerMqtt {
        // tcp://MQTT安装的服务器地址:MQTT定义的端口号    public static final String HOST = "tcp://localhost:1883";
        // 定义一个主题    public static final String TOPIC = "root/topic/123";
        // 定义MQTT的ID，可以在MQTT服务配置中指定    private static final String clientid = "server11";
        private MqttClient client;
        private MqttTopic topic11;
        private String userName = "mosquitto";
        private String passWord = "mosquitto";
        private MqttMessage message;

        /**
         * 构造函数     *     * @throws MqttException
         */
        public ServerMqtt() throws MqttException {        // MemoryPersistence设置clientid的保存形式，默认为以内存保存        client = new MqttClient(HOST, clientid, new MemoryPersistence());        connect();    }    /**     * 用来连接服务器     */    private void connect() {
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(false);

            options.setUserName(userName);
            options.setPassword(passWord.toCharArray());        // 设置超时时间        options.setConnectionTimeout(10);        // 设置会话心跳时间        options.setKeepAliveInterval(20);        try {            client.setCallback(new PushCallback());            client.connect(options);            topic11 = client.getTopic(TOPIC);        } catch (Exception e) {            e.printStackTrace();        }    }    /**     *     * @param topic     * @param message     * @throws MqttPersistenceException     * @throws MqttException     */    public void publish(MqttTopic topic, MqttMessage message) throws MqttPersistenceException, MqttException {        MqttDeliveryToken token = topic.publish(message);        token.waitForCompletion();        System.out.println("message is published completely! " + token.isComplete());
            client = new MqttClient(HOST, clientid, new MemoryPersistence());
            topic11 = client.getTopic(TOPIC);
        }
    }

    static class ClientMqtt {

        private MqttClient client;
        private MqttConnectOptions options;
        private String userName = "admin";
        private String passWord = "public";
        private ScheduledExecutorService scheduler;
        private MqttMessage mqttMessage = new MqttMessage(){
            @Override
            public void setQos(int qos) {
                super.setQos(1);
            }

            @Override
            public void setPayload(byte[] payload) {
                super.setPayload(("hello，MQTT").getBytes());
            }
        };

        private void start() {
            try {
                // host为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
                client = new MqttClient(HOST, clientid, new MemoryPersistence());
                // MQTT的连接设置
                options = new MqttConnectOptions();
                // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
                options.setCleanSession(true);
                // 设置连接的用户名
                options.setUserName(userName);
                // 设置连接的密码
                options.setPassword(passWord.toCharArray());
                // 设置超时时间 单位为秒
                options.setConnectionTimeout(10);
                // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
                options.setKeepAliveInterval(20);
                // 设置回调
                client.setCallback(new PushCallback());
                MqttTopic topic = client.getTopic(TOPIC);
                // setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
                options.setWill(topic, "close".getBytes(), 2, true);
                client.connect(options);

                MqttTopic mqttTopic = client.getTopic(TOPIC);
                // 订阅消息
                int[] Qos = {1};
                String[] topic1 = {TOPIC};
                client.subscribe(topic1, Qos);

                MqttDeliveryToken token = mqttTopic.publish(mqttMessage);



            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static void main(String[] args) throws MqttException {
            ClientMqtt client = new ClientMqtt();
            client.start();
        }
    }

    public static class PushCallback implements MqttCallback {
        public void connectionLost(Throwable cause) {
            // 连接丢失后，一般在这里面进行重连
            System.out.println("连接断开，可以做重连");
        }

        public void deliveryComplete(IMqttDeliveryToken token) {
            System.out.println("deliveryComplete---------" + token.isComplete());
        }

        public void messageArrived(String topic, MqttMessage message) throws Exception {
            // subscribe后得到的消息会执行到这里面
            System.out.println("接收消息主题 : " + topic);
            System.out.println("接收消息Qos : " + message.getQos());
            System.out.println("接收消息内容 : " + new String(message.getPayload()));
        }
    }
}