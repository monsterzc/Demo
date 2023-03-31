package com.can.config;

import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @className: KafkaConsumer
 * @description: Kafka消费者
 * @author: zhengcan
 * @date: 2023/3/31
 **/
@Slf4j
@Component
public class KafkaConsumer {


    @KafkaListener(topics = KafkaProducer.TOPIC_TEST, groupId = KafkaProducer.TOPIC_GROUP)
    public void topicTest(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());

        message.ifPresent((Object msg)->{
            msg = message.orElse(null);
            log.info("Topic:"+topic+",Message:"+msg+"partition"+record.partition()+" class"+msg.getClass());
            String task = msg.toString();
            List<Integer> integers = JSONArray.parseArray(task, Integer.class);

            integers.forEach(param->{
                ThreadPoolManager.addExecuteTask("java",param);
            });
            ack.acknowledge();
        });

    }

    @KafkaListener(topics = KafkaConfig.TOPIC_NAME)
    public void handler(String message) {
        System.out.println("收到消息："+message);
    }

    /**
     * 监听一个或者多个Topic
     */
    @KafkaListener(topics = KafkaConfig.TOPIC_NAME, containerFactory = "kafkaManualAckListenerContainerFactory")
    public void handler(String message, Acknowledgment ack) {
        System.out.println("收到消息："+message);
        // 确认收到消息
        ack.acknowledge();
    }

}
