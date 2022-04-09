package com.hk.rocketmq.consumer.listener;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author : HK意境
 * @ClassName : ConsumerListener
 * @date : 2022/4/8 15:00
 * @description : 自定义消息消费的逻辑，消息监听器
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Component
@RocketMQMessageListener(consumerGroup = "${rocketmq.producer.group}",topic = "spring-boot-rocketmq",consumeMode = ConsumeMode.ORDERLY)
public class ConsumerListener implements RocketMQListener<String> {


    // 当消息被接收到的时候的消费逻辑
    @Override
    public void onMessage(String s) {

        System.out.println("消息被消费：" + s);

    }
}
