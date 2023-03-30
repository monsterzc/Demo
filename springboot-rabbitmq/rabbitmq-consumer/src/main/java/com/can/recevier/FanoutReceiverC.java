package com.can.recevier;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * @className: FanoutReceiverA
 * @description: TODO 类描述
 * @author: zhengcan
 * @date: 2023/3/30
 **/

@Component
@RabbitListener(queues = "fanout.C")
public class FanoutReceiverC {

    @RabbitHandler
    public void process(Map messageMap) {
        System.out.println("FanoutReceiverC消费者接收到消息："+messageMap.toString());
    }
}
