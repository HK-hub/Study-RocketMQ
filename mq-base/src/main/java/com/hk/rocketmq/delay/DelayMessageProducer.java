package com.hk.rocketmq.delay;

import lombok.extern.log4j.Log4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

/**
 * @author : HK意境
 * @ClassName : DelayMessageProducer
 * @date : 2022/4/7 12:43
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Log4j
public class DelayMessageProducer {

    public static String namesrvAddr = "42.193.55.146";
    public static String port = ":9876";

    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {

        // 创建消息生产者
        DefaultMQProducer producer = new DefaultMQProducer("delay_producer");
        producer.setNamesrvAddr(namesrvAddr +port);
        producer.setInstanceName("delay_producer");


        producer.start();

        // 生产消息
        Message message = new Message("delay_topic", "delay", "this is a delay message!".getBytes(StandardCharsets.UTF_8));

        // 设置消息延时等级：1s 5s 10s 20s 30s 1m 2m 3m 4m 5m 6m 10m 30m 1h 2h
        message.setDelayTimeLevel(2);

        // 发送消息
        SendResult result = producer.send(message);


        // 解析结果
        log.info(result);

        // 关闭消息生产者
        producer.shutdown();


    }


}
