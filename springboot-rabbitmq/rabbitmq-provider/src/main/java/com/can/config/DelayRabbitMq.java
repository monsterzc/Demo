package com.can.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @className: DelayRabbitMq
 * @description: 延时队列配置
 * @author: zhengcan
 * @date: 2023/4/1
 **/
@Configuration
public class DelayRabbitMq {

    /**
     * 队列的名字
     */
    public static final String DELAY_QUEUE_A = "delay.queue.a";

    public static final String DELAY_QUEUE_B = "delay.queue.b";

    /**
     * 交换机的名字
     */
    public static final String DELAY_EXCHANGE = "delay.exchange";

    /**
     * routing key
     */
    public static final String DELAY_ROUTING_KEY_A = "delay.routingKey.a";

    public static final String DELAY_ROUTING_KEY_B = "delay.routingKey.b";

    /**
     * 定义队列
     */
    @Bean
    public Queue delayQueueA(){
        return new Queue(DELAY_QUEUE_A);
    }

    @Bean
    public Queue delayQueueB(){
        return new Queue(DELAY_QUEUE_B);
    }

    /**
     * 声明交换机,自定义交换机-------延迟交换机
     */
    @Bean
    CustomExchange delayExchange(){
        Map<String, Object> args = new HashMap<>(2);
        // 自定义交换机的类型
        args.put("x-delayed-type","direct");
        return new CustomExchange(DELAY_EXCHANGE,"x-delayed-message",true,false,args);
    }

    /**
     * 绑定交换机
     */
    @Bean
    Binding delayBindingA(@Qualifier("delayQueueA")Queue queue,@Qualifier("delayExchange")CustomExchange customExchange){
        return BindingBuilder.bind(queue).to(customExchange).with(DELAY_ROUTING_KEY_A).noargs();
    }

    @Bean
    Binding delayBindingB(@Qualifier("delayQueueB")Queue queue,@Qualifier("delayExchange")CustomExchange customExchange){
        return BindingBuilder.bind(queue).to(customExchange).with(DELAY_ROUTING_KEY_B).noargs();
    }


}
