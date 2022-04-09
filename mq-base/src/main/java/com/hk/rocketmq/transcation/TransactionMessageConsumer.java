package com.hk.rocketmq.transcation;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author : HK意境
 * @ClassName : TransactionMessageConsumer
 * @date : 2022/4/7 19:47
 * @description : 事务消息接收者
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class TransactionMessageConsumer {

    public static String namesrvAddr = "42.193.55.146";
    public static String port = ":9876";

    public static void main(String[] args) throws MQClientException {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("batch_topic");
        consumer.setNamesrvAddr(namesrvAddr+port);

        // 订阅消息: 过滤消息———— Tag 标签过滤

        // 消费 filter_sql_topic 主题下的 满足sql 条件的消息类型
        consumer.subscribe("transaction_topic","taga || tagb || tagc");

        //consumer.subscribe("filter_topic","*");

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

                list.forEach(message -> {
                    System.out.println(message.getUserProperty("i"));
                    System.out.println(message.getMsgId());
                });

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS ;
            }
        });

        System.out.println("开始监听消息...");
        consumer.start();

    }


}
