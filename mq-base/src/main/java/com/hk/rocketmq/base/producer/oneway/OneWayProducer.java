package com.hk.rocketmq.base.producer.oneway;


import lombok.extern.log4j.Log4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;


import java.nio.charset.StandardCharsets;

/**
 * @author : HK意境
 * @ClassName : OneWayProducer
 * @date : 2022/4/6 20:13
 * @description : 发送单向消息: 
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */

@Log4j
public class OneWayProducer {



    public static String namesrvAddr = "42.193.55.146";
    public static String port = ":9876";

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException {

        log.info("this is logger");

        // 1. 创建消息生产者
        DefaultMQProducer producer = new DefaultMQProducer("group1");

        // 2. 设置 namesrv address
        producer.setNamesrvAddr(namesrvAddr+port);

        // 3. 开启消息生产者
        producer.start();

        // 4. 创建单向消息
        Message message = new Message("base","oneWayMsg","this is a oneway message".getBytes(StandardCharsets.UTF_8));

        // 5. 发送单向消息
        producer.sendOneway(message);

        // 6. 关闭消息生产者
        producer.shutdown();

    }


}
