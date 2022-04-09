package com.hk.rocketmq.delay;

import lombok.extern.log4j.Log4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import java.util.List;

/**
 * @author : HK意境
 * @ClassName : DelayMessageConsumer
 * @date : 2022/4/7 12:43
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Log4j
public class DelayMessageConsumer {

    public static String namesrvAddr = "42.193.55.146";
    public static String port = ":9876";

    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {

        // 创建消息消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("delay_group");

        consumer.setNamesrvAddr(namesrvAddr+port);

        // 订阅消息主题
        consumer.subscribe("delay_topic","delay_tag");

        // 注册消息消费监听器: 指定消息消费的逻辑
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

                list.forEach(message -> {
                    long l = System.currentTimeMillis() - message.getStoreTimestamp();

                    System.out.println(new String(message.getBody()) + ", 延迟时间："
                            + l);
                });

                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        });

        // 开启消费者
        consumer.start();

    }

}
