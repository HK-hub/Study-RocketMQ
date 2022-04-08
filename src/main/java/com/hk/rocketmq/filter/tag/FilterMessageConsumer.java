package com.hk.rocketmq.filter.tag;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author : HK意境
 * @ClassName : FilterMessageConsumer
 * @date : 2022/4/7 14:33
 * @description : 过滤消息消费者: 标签过滤
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class FilterMessageConsumer {


    public static String namesrvAddr = "42.193.55.146";
    public static String port = ":9876";


    public static void main(String[] args) throws MQClientException {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("batch_topic");
        consumer.setNamesrvAddr(namesrvAddr+port);

        // 订阅消息: 过滤消息———— Tag 标签过滤

        // 消费 filter_topic 主题下的所有标签消息
        consumer.subscribe("filter_topic","*");

        // 消费 filter_topic 主题下的标签为 tag1 的消息
        consumer.subscribe("filter_topic","tag1");

        // 消费 filter_topic 主题下的标签为 tag1，tag2 和 tag3 的消息
        consumer.subscribe("filter_topic","tag1 || tag2 || tag3");

        //consumer.subscribe("filter_topic","*");

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
