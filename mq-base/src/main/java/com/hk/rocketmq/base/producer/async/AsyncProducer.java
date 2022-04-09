package com.hk.rocketmq.base.producer.async;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

/**
 * @author : HK意境
 * @ClassName : AsyncProducer
 * @date : 2022/4/6 19:53
 * @description : 异步消息生产者: 发送异步消息，一般用于对于响应速度要求严格的场景下，我们一样的可以通过指定一个 callback回调函数来获取消息发送返回值
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class AsyncProducer {

    public static String namesrvAddr = "42.193.55.146";
    public static String port = ":9876";

    // 发送异步消息
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException {

        // 1. 创建消息生产者，设置消息分组
        DefaultMQProducer asyncProducer =  new DefaultMQProducer("async_message_group");
        asyncProducer.setNamesrvAddr(namesrvAddr+port);

        // 2. 启动消息生产者
        asyncProducer.start();

        // 3. 创建一个异步消息
        Message message = new Message("base", "async_message", "parallel acumulate".getBytes(StandardCharsets.UTF_8));

        // 4. 发送异步消息 并设置回调函数
        asyncProducer.send(message, new SendCallback() {

            /**
             * 发送成功的回调函数
             * @param sendResult
             */
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println(sendResult);
            }


            /**
             * 发送失败的回调函数
             * @param throwable
             */
            @Override
            public void onException(Throwable throwable) {
                System.out.println("发送异常： " + throwable.getMessage());
            }
        });

        System.out.println("后续的业务代码");

        // 关闭消息生产者
        asyncProducer.shutdown();





    }


}
