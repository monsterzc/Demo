package com.can.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @className: RabbitConfig
 * @description: TODO 类描述
 * @author: zhengcan
 * @date: 2023/3/30
 **/

@Slf4j
@Configuration
public class RabbitConfig {

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        // 设置开启Mandatory,才能出发回调函数，无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);

        // 消息发送到Exchange的回调，无论成功与否
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("ConfirmCallback: 相关数据：{}",correlationData);
                log.info("ConfirmCallback: 确认情况：{}",ack);
                log.info("ConfirmCallback: 原因：{}",cause);
            }
        });

        // 消息从Exchange路由到Queue失败的回调
        rabbitTemplate.setReturnsCallback(returnedMessage ->
                log.info("ReturnsCallback:消息{}",JSON.toJSONString(returnedMessage))
        );
        return rabbitTemplate;
    }
}
