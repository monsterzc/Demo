package com.can.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @className: KafkaProducer
 * @description: Kafka生产者
 * @author: zhengcan
 * @date: 2023/3/31
 **/
@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    /**
     * 自定义topic
     */
    public static final String TOPIC_TEST = "can";

    public static final String TOPIC_GROUP = "topic.group1";

    public void send(Object obj){
        kafkaTemplate.send(TOPIC_TEST, 0, "" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),obj);
    }


}
