package com.hk.rocketmq.order;

import lombok.extern.log4j.Log4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * @author : HK意境
 * @ClassName : OrderedMessageConsumer
 * @date : 2022/4/7 8:39
 * @description : 顺序消息消费者
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Log4j
public class OrderedMessageConsumer {


    public static String namesrvAddr = "42.193.55.146";
    public static String port = ":9876";

    public static void main(String[] args) throws MQClientException {

        // 创建消息消费者
        DefaultMQPushConsumer pushConsumer = new DefaultMQPushConsumer("ordered_group");
        // 设置 namesrv_addr
        pushConsumer.setNamesrvAddr(namesrvAddr+port);


        // 设置消息模式: 负载均衡模式
        pushConsumer.setMessageModel(MessageModel.CLUSTERING);

        // 订阅消息 : orders 主题下的所有 tag 标签都可以
        pushConsumer.subscribe("orders","*");

        // 注册消息监听器
        pushConsumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {

                for (MessageExt messageExt : list) {
                    System.out.println("消费消息："+ new String(messageExt.getBody()));
                }

                return ConsumeOrderlyStatus.SUCCESS;
            }

        });


        // 开启消费者进行消息消费
        pushConsumer.start();
        log.info("消息消费者启动成功");
    }



}
