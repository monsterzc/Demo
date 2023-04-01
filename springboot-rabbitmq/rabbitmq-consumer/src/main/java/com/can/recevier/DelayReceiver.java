package com.can.recevier;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Date;
import java.util.Map;

/**
 * @className: DelayReceiver
 * @description: 延时队列消费者
 * @author: zhengcan
 * @date: 2023/4/1
 **/
@Slf4j
@Component
public class DelayReceiver {

    @RabbitListener(queues = "delay.queue.a")
    @RabbitHandler
    public void handler(Message message,Channel channel) {
        try {
            log.info("延时队列收到消息>>>>>>");
            String msg = message.toString();
            byte[] body = message.getBody();
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(body));
            Map<String, String> msgMap = (Map<String, String>) ois.readObject();
            String messageId = msgMap.get("messageId");
            String messageData = msgMap.get("messageData");
            String createTime = msgMap.get("createTime");
            String type = msgMap.get("type");
            log.info("延时队列收到消息>>>>>>messageId:{},messageData:{},createTime:{},type:{},msg:{}",messageId,messageData,createTime,type,msg);
            //channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RabbitListener(queues = "delay.queue.b")
    @RabbitHandler
    public void receiveD(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("当前时间：{},延时队列收到消息：{}", new Date(), msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
