package com.can.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Map;

/**
 * @className: MyAckReceiver
 * @description: TODO 类描述
 * @author: zhengcan
 * @date: 2023/3/30
 **/

@Component
public class MyAckReceiver implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {
            byte[] body = message.getBody();
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(body));
            Map<String,String> msgMap = (Map<String,String>) ois.readObject();
            String messageId = msgMap.get("messageId");
            String messageData = msgMap.get("messageData");
            String createTime = msgMap.get("createTime");
            String type=msgMap.get("type");
            ois.close();
            if ("TestDirectQueue".equals(message.getMessageProperties().getConsumerQueue())){
                 System.out.println("消费成功 messageId:"+messageId+"messageData:"+messageData+"createTime:"+createTime+"type:"+type);
                 System.out.println("消费的主题消息来自："+message.getMessageProperties().getConsumerQueue());
                System.out.println("执行TestDirectQueue中的消息的业务处理流程......");
            }
            if ("fanout.A".equals(message.getMessageProperties().getConsumerQueue())){
                System.out.println("消费成功 messageId:"+messageId+"messageData:"+messageData+"createTime:"+createTime+"type:"+type);
                System.out.println("消费的主题消息来自："+message.getMessageProperties().getConsumerQueue());
                System.out.println("执行fanoutA中的消息的业务处理流程......");
            }
            // System.out.println("MyAckReceiver messageId:"+messageId+"messageData:"+messageData+"createTime:"+createTime);
            // System.out.println("消费的主题消息来自："+message.getMessageProperties().getConsumerQueue());
            //第二个参数，手动确认可以被批处理，当该参数为 true 时，则可以一次性确认 delivery_tag 小于等于传入值的所有消息
            channel.basicAck(deliveryTag,true);
            // channel.basicReject(deliveryTag, true); 第二个参数，true会重新放回队列，所以需要自己根据业务逻辑判断什么时候使用拒绝

        } catch (Exception e) {
            channel.basicReject(deliveryTag,false);
            e.printStackTrace();
        }


    }
}
