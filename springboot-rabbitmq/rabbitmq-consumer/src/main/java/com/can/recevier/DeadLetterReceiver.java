package com.can.recevier;

import com.can.config.DeadLetterRabbitConfig;
import com.can.utils.MapStringToMapUtil;
import com.rabbitmq.client.Channel;
import com.sun.istack.internal.NotNull;
import lombok.NonNull;
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
 * @className: DeadLetterReceiver
 * @description: TODO 类描述
 * @author: zhengcan
 * @date: 2023/3/30
 **/
@Slf4j
@Component
public class DeadLetterReceiver {

    @RabbitListener(queues = "dead.letter.deadletter.queuea")
    @RabbitHandler
    public void deadLetterConsumer1(@NotNull Message message, Channel channel) {
        try {
        byte[] body = message.getBody();
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(body));
        Map<String, String> msgMap = (Map<String, String>) ois.readObject();
        String messageId = msgMap.get("messageId");
        String messageData = msgMap.get("messageData");
        String createTime = msgMap.get("createTime");
        log.info("死信队列接收到的消息为:MyAckReceiver messageId:" + messageId, "messageData:{}", messageData, "createTime:{}", createTime);
        log.info("消费的主题消息来自:{}", message.getMessageProperties().getConsumerQueue());

        channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        } catch (Exception e) {
            log.info("死信队列中消息的消费失败", e);
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "dead.letter.deadletter.queueb")
    @RabbitHandler
    public void deadLetterConsumer2(Message message, Channel channel) {
        try {
        byte[] body = message.getBody();
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(body));
        Map<String, String> msgMap = (Map<String, String>) ois.readObject();
        String messageId = msgMap.get("messageId");
        String messageData = msgMap.get("messageData");
        String createTime = msgMap.get("createTime");
        log.info("死信队列接收到的消息为:MyAckReceiver messageId:" + messageId, "messageData:{}", messageData, "createTime:{}", createTime);
        log.info("消费的主题消息来自:{}", message.getMessageProperties().getConsumerQueue());

        channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        } catch (Exception e) {
            log.info("死信队列中消息的消费失败", e);
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = DeadLetterRabbitConfig.QUEUE)
    @RabbitHandler
    public void buinessMsg(Message message, Channel channel) throws IOException {
        try {
            byte[] body = message.getBody();
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(body));
            Map<String,String> msgMap = (Map<String,String>) ois.readObject();
            String messageId = msgMap.get("messageId");
            String messageData = msgMap.get("messageData");
            String createTime = msgMap.get("createTime");
            String type=msgMap.get("type");
            ois.close();
            log.info("收到死信信息：messageId："+messageId,"messageData:{}",messageData,"createTime:{}",createTime,"type:{}",type);
            if (messageData.startsWith("false")) {
                log.info("消费失败:{}" ,message);
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            } else {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = DeadLetterRabbitConfig.DEAD_LETTER_QUEUE)
    @RabbitHandler
    public void deadLetterMsg(Message message, Channel channel) throws IOException {
        try {
            byte[] body = message.getBody();
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(body));
            Map<String,String> msgMap = (Map<String,String>) ois.readObject();
            String messageId = msgMap.get("messageId");
            String messageData = msgMap.get("messageData");
            String createTime = msgMap.get("createTime");
            String type=msgMap.get("type");
            ois.close();
            log.info("收到死信信息：messageId："+messageId,"messageData:{}",messageData,"createTime:{}",createTime,"type:{}",type);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
