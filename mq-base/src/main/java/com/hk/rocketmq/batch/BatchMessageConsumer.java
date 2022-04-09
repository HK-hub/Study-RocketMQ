package com.hk.rocketmq.batch;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author : HK意境
 * @ClassName : BatchMessageConsumer
 * @date : 2022/4/7 13:16
 * @description : 批量消息消费者
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class BatchMessageConsumer {

    public static String namesrvAddr = "42.193.55.146";
    public static String port = ":9876";


    public static void main(String[] args) throws MQClientException {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("batch_topic");
        consumer.setNamesrvAddr(namesrvAddr+port);

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

                list.forEach(message -> {
                    System.out.println(message.getMsgId());
                });

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS ;
            }
        });

        System.out.println("开始监听消息...");
        consumer.start();

    }

}
