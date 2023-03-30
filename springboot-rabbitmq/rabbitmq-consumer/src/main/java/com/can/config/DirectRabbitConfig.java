package com.can.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @className: DirectRabbitConfig
 * @description: TODO 类描述
 * @author: zhengcan
 * @date: 2023/3/30
 **/
@Configuration
public class DirectRabbitConfig {

    @Bean
    public Queue TestDirectQueue(){
        return new Queue("TestDirectQueue",true);
    }

    @Bean
    DirectExchange TestDirectExchange(){
        return new DirectExchange("TestDirectExchange");
    }

    @Bean
    Binding bindingDirect(){
        return BindingBuilder.bind(TestDirectQueue()).to(TestDirectExchange()).with("TestDirectRouting");
    }

    @Bean
    DirectExchange lonelyDirectExchange() {
        return new DirectExchange("lonelyDirectExchange");
    }

}
