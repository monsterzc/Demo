package com.can.recevier;

import com.rabbitmq.client.Channel;
import com.sun.istack.internal.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @className: DeadLetterAckReceiver
 * @description: TODO 类描述
 * @author: zhengcan
 * @date: 2023/3/30
 **/

@Slf4j
@Component
public class DeadLetterAckReceiver {

    /**
     * 业务队列手动确认消息
     * @param message
     * @param channel
     */
    @RabbitListener(queues = "dead.letter.business.queuea")
    @RabbitHandler
    public void deadLetterProcess1(Message message, Channel channel) {

        try {
            // 直接拒绝消费该消息，后面的参数一定是false，否则会重新进入业务队列，不会进入死信队列
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
            System.out.println("拒绝签收...消息的路由键为:"+message.getMessageProperties().getReceivedRoutingKey());
            log.info("拒绝签收...消息的路由键为：{}",message.getMessageProperties().getReceivedRoutingKey());
        } catch (Exception e) {
            log.info("消息拒绝签收失败",e);
        }
    }

    @RabbitListener(queues = "dead.letter.business.queueb")
    @RabbitHandler
    public void deadLetterProcess2(Message message,Channel channel){

        try {
            // 直接拒绝消费该消息，后面的参数一定是false，否则会重新进入业务队列，不会进入死信队列
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
            log.info("拒绝签收...消息的路由键为：{}",message.getMessageProperties().getReceivedRoutingKey());
            System.out.println("拒绝签收...消息的路由键为："+message.getMessageProperties().getReceivedRoutingKey());
        } catch (Exception e) {
            log.info("消息拒绝签收失败:",e);
        }
    }


}
