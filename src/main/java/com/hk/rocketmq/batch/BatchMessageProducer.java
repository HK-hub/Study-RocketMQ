package com.hk.rocketmq.batch;

import lombok.extern.log4j.Log4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author : HK意境
 * @ClassName : BatchMessageProducer
 * @date : 2022/4/7 13:12
 * @description : 发送批量消息
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Log4j
public class BatchMessageProducer {

    public static String namesrvAddr = "42.193.55.146";
    public static String port = ":9876";

    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {

        //1. 创建消息发送者，指定生产者组名
        DefaultMQProducer mqProducer = new DefaultMQProducer("group1");

        //2. 指定NamesrvAddr
        mqProducer.setNamesrvAddr(namesrvAddr+port);

        //3. 启动Producer 消息生产者
        mqProducer.start();

        //4. 创建消息对象，包括 Topic 主题，Tag 标签，消息体
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            messages.add(new Message("batch_topic","batch_message",
                    ("this is a batch message:"+1).getBytes(StandardCharsets.UTF_8)));

        }

        // 批量发送消息
        SendResult result = mqProducer.send(messages);

        // 解析消息发送结果
        log.info("消息发送结果："+result.toString());

        // 关闭消息生产者
        mqProducer.shutdown();


    }


}
