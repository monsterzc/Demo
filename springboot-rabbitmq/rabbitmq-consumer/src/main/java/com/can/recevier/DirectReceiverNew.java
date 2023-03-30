package com.can.recevier;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @className: DirectReceiver
 * @description: 直连交换机监听类
 * @author: zhengcan
 * @date: 2023/3/30
 **/
@Component
@RabbitListener(queues = "TestDirectQueue") //监听的队列名称
public class DirectReceiverNew {

    @RabbitHandler
    public void process(Map testMessage) {
        System.out.println("DirectReceiverNew消费者收到消息:"+testMessage.toString());
    }

}
