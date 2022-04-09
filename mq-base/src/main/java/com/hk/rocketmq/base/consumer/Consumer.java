package com.hk.rocketmq.base.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * @author : HK意境
 * @ClassName : Consumer
 * @date : 2022/4/6 20:42
 * @description : 消息消费者
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class Consumer {

    public static String namesrvAddr = "42.193.55.146";
    public static String port = ":9876";

    public static void main(String[] args) throws MQClientException {

        // 1. 创建消息消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");

        // 2. 设置NameSrcAddr
        consumer.setNamesrvAddr(namesrvAddr+port);

        // 3. 订阅主题 Topic , Tag
        consumer.subscribe("base","tag1");

        // 设定消费模式：广播模式，单播模式(负载均衡模式)
        // 负载均衡模式
        consumer.setMessageModel(MessageModel.CLUSTERING);

        // 4. 设置回调函数处理消息
        consumer.setMessageListener(new MessageListenerConcurrently() {

            // 接受消息内容的方法
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

                list.forEach(System.out::println);

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS ;
            }
        });

        // 5. 启动消费者进行消息消费
        consumer.start();

        // 关闭消息消费者
        //consumer.shutdown();


    }


}
