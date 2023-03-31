package com.can.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

/**
 * @className: KafkaConfig
 * @description: Kafka配置类
 * @author: zhengcan
 * @date: 2023/3/31
 **/

@Configuration
public class KafkaConfig {

    @Autowired
    private ConsumerFactory consumerFactory;

    public static final String TOPIC_NAME = "test";

    @Bean
    public NewTopic batchTopic(){
        return new NewTopic("can",2,(short)1);
    }

    @Bean
    public NewTopic topicHello(){
        // 创建topic：topic名字，partition数量，replicas副本数量
        return TopicBuilder.name(KafkaConfig.TOPIC_NAME).build();
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer,String>> kafkaManualAckListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        // 设置提交偏移量的方式 当Acknowledgement.acknowledge()侦听器调用该方法时，立即提交偏移量
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }
}
