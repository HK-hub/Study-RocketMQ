package com.hk.rocketmq.base.producer.sync;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author : HK意境
 * @ClassName : SyncMessageProducer
 * @date : 2022/4/6 16:41
 * @description : 同步消息生产者，发送同步消息
 * @Todo :
 *      1. 创建消息发送者，指定生产者组名
 *
 *
 *
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class SyncMessageProducer {

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
        for (int i = 0; i < 10; i++) {

            /**
             * 消息参数设置：
             *      topic 主题：表示消息的类别
             *      tag : 消息标签
             *      content：消息数据内容
             *
             */
            Message message = new Message("baseMessage","tag1",("hello rocketMQ! "+i).getBytes(StandardCharsets.UTF_8));

            // 发送消息
            SendResult result = mqProducer.send(message);

            // 获取消息发送结果
            // 消息发送状态
            SendStatus status = result.getSendStatus();
            // 消息ID
            String msgId = result.getMsgId();
            // 消息队列ID
            int queueId = result.getMessageQueue().getQueueId();
            //
            System.out.println("消息ID："+msgId+" , 消息发送状态："+status + ", 消息队列ID："+queueId);

            TimeUnit.SECONDS.sleep(1);




        }

        // 关闭消息生产者
        mqProducer.shutdown();


    }




}
