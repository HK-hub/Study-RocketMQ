package com.hk.rocketmq.transcation;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

/**
 * @author : HK意境
 * @ClassName : TranscationMessageProducer
 * @date : 2022/4/7 19:46
 * @description : transaction 事务消息发送者
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class TranscationMessageProducer {
    public static String namesrvAddr = "42.193.55.146";
    public static String port = ":9876";

    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {

        // 创建事务消息生产者
        TransactionMQProducer producer = new TransactionMQProducer("transaction_group");

        // 绑定 namesrvaddr
        producer.setNamesrvAddr(namesrvAddr+port);

        // 设置事务监听器 : 执行本地事务的入口程序
        producer.setTransactionListener(new TransactionListener() {

            // 在该方法中去执行本地事务
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {

                // 在这里面处理执行本地事务的逻辑
                if("taga".equals(message.getTags())){
                    // 执行本地事务，并且提交
                    System.out.println("执行本地事务成功，提交");
                    return LocalTransactionState.COMMIT_MESSAGE ;

                }else if("tagb".equals(message.getTags())){
                    // 执行本地事务失败，进行回滚
                    System.out.println("执行本地事务失败，进行回滚");
                    return LocalTransactionState.ROLLBACK_MESSAGE ;

                }else if("tagc".equals(message.getTags())){

                    //
                    System.out.println("本地事务状态还未知道，请继续检查");
                    return LocalTransactionState.UNKNOW;
                }

                // 默认情况
                return LocalTransactionState.UNKNOW;
            }


            // 检查本地事务的执行状态 ：进行事务消息状态的回查方法
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {

                System.out.println(messageExt.getTopic()+"-"+messageExt.getTags()+"-"+messageExt.getMsgId());

                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });

        // 启动消息生产者
        producer.start();

        // 生产消息
        // 发送消息
        String[] tags = {"taga","tagb","tagc"};
        for (int i = 0; i < 3; i++) {

            Message message = new Message("transaction_topic", tags[i],"this is a transaction message".getBytes(StandardCharsets.UTF_8));

            producer.sendMessageInTransaction(message,null);

        }

        // 关闭消息生产者
        producer.shutdown();


    }

}
