package com.hk.rocketmq.producer.test;

import com.hk.rocketmq.producer.MQProducerApplication;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;


/**
 * @author : HK意境
 * @ClassName : MQProducerTest
 * @date : 2022/4/8 14:32
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MQProducerApplication.class)
public class MQProducerTest {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Test
    public void sendMessageTest(){

        // 发送字符串消息
        System.out.println(rocketMQTemplate.getClass());
        rocketMQTemplate.convertAndSend("spring-boot-rocketmq","hello spring-boot-rocketmq".getBytes(StandardCharsets.UTF_8));

    }


}
