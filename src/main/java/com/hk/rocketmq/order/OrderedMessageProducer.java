package com.hk.rocketmq.order;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author : HK意境
 * @ClassName : OrderedMessageProducer
 * @date : 2022/4/6 22:00
 * @description : 顺序消息生产者
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class OrderedMessageProducer {

    public static String namesrvAddr = "42.193.55.146";
    public static String port = ":9876";

    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {

        // 创建消息生产者，指定消息生产者的组
        DefaultMQProducer producer = new DefaultMQProducer("ordered_group");
        producer.setNamesrvAddr(namesrvAddr + port);
        producer.start();



        // 创建消息
        //Message message = new Message("order records", "orders", "cerate a order".getBytes(StandardCharsets.UTF_8));
        OrderStep orderStep = new OrderStep();
        List<OrderStep> orders = orderStep.buildOrders();


        // 发送消息
        int i = 0;
        for (OrderStep order : orders) {
            /***
             * send() 参数列表：
             *      参数一：message 消息对象
             *      参数二：消息队列选择器
             *      参数三：选择队列的业务标识（订单ID）
             */
            Message message = new Message("orders", "order", "" + i++, order.toString().getBytes(StandardCharsets.UTF_8));
            producer.send(message,
                    // 消息队列选择器
                    new MessageQueueSelector() {
                        /**
                         *
                         * @param list 该主题下的所有消息队列
                         * @param message：要发送的消息对象
                         * @param o 业务标识参数
                         * @return
                         */
                        @Override
                        public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                            long orderID = (long) o;

                            // 根据消息的业务标识来选择消息队列
                            long index = orderID % list.size();
                            return list.get((int) index);
                        }
                    }, order.getOrderId());


            // 关闭生产者
            producer.shutdown();

        }

    }

}