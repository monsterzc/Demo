package com.can.config;

import com.can.constant.Constant;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @className: DeadLetterConfig
 * @description: 私信队列配置
 * @author: zhengcan
 * @date: 2023/3/30
 **/
@Configuration
public class DeadLetterConfig {

    /**
     *
     * 业务队列A
     */
    @Bean
    public Queue businessQueueA(){
        Map<String, Object> args = new HashMap<>();
        // x-dead-letter-exchange： 这里声明当前业务队列绑定的死信队列交换机
        args.put("x-dead-letter-exchange",Constant.DEAD_LETTER_EXCHANGE);
        // x-dead-letter-routing-key: 这里声明当前业务队列绑定的死信路由key
        args.put("x-dead-letter-routing-key",Constant.DEAD_LETTER_QUEUE_A_ROUTING_KEY);
        return new Queue(Constant.BUSINESS_QUEUE_A,true,false,false,args);
    }

    /**
     *
     * 业务队列B
     */
    @Bean
    public Queue businessQueueB(){
        Map<String, Object> args = new HashMap<>();
        // x-dead-letter-exchange： 这里声明当前业务队列绑定的死信队列交换机
        args.put("x-dead-letter-exchange",Constant.DEAD_LETTER_EXCHANGE);
        // x-dead-letter-routing-key：这里声明当前业务队列绑定的死信路由key
        args.put("x-dead-letter-routing-key",Constant.DEAD_LETTER_QUEUE_B_ROUTING_KEY);
        return new Queue(Constant.BUSINESS_QUEUE_B, true, false, false, args);
    }

    /**
     * 业务队列交换机
     */
    @Bean
    TopicExchange businessTopicExchange(){
        return new TopicExchange(Constant.BUSINESS_EXCHANGE,true,false);
    }

    /**
     * 业务队列A与交换机绑定，并指定routing key
     *
     * @return
     */
    @Bean
    Binding businessBindingA(){
        return BindingBuilder.bind(businessQueueA()).to(businessTopicExchange()).with(Constant.BUSINESS_QUEUE_A_ROUTING_KEY);
    }

    /**
     * 业务队列A与交换机绑定，并指定routing key
     *
     * @return
     */
    @Bean
    Binding businessBindingB(){
        return BindingBuilder.bind(businessQueueB()).to(businessTopicExchange()).with(Constant.BUSINESS_QUEUE_B_ROUTING_KEY);
    }

    /**
     * 死信队列A
     */
    @Bean
    public Queue deadLetterQueueA(){
        return new Queue(Constant.DEAD_LETTER_QUEUE_A);
    }

    /**
     * 死信队列B
     */
    @Bean
    public Queue deadLetterQueueB(){
        return new Queue(Constant.DEAD_LETTER_QUEUE_B);
    }

    /**
     * 死信队列交换机
     */

    @Bean
    Exchange deadLetterDirectExchange(){
        return new DirectExchange(Constant.DEAD_LETTER_EXCHANGE);
    }

    /**
     * 死信队列A与交换机进行绑定并指定routing key
     * @return
     */
    @Bean
    BindingBuilder.GenericArgumentsConfigurer deadLetterBindingA(){
        return BindingBuilder.bind(deadLetterQueueA()).to(deadLetterDirectExchange()).with(Constant.DEAD_LETTER_QUEUE_A_ROUTING_KEY);
    }

    /**
     * 死信队列B与交换机进行绑定并指定routing key
     * @return
     */
    @Bean
    BindingBuilder.GenericArgumentsConfigurer deadLetterBindingB(){
        return BindingBuilder.bind(deadLetterQueueB()).to(deadLetterDirectExchange()).with(Constant.DEAD_LETTER_QUEUE_B_ROUTING_KEY);
    }

    /**
     * 使用 RabbitAdmin 启动服务便创建交换机和队列
     */

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        // 只有设置为true，Spring才会加载RabbitAdmin这个类
        rabbitAdmin.setAutoStartup(true);
        //创建死信交换机和队列
        rabbitAdmin.declareExchange(deadLetterDirectExchange());
        rabbitAdmin.declareQueue(deadLetterQueueA());
        rabbitAdmin.declareQueue(deadLetterQueueB());
        //创建业务交换机和队列
        rabbitAdmin.declareExchange(businessTopicExchange());
        rabbitAdmin.declareQueue(businessQueueA());
        rabbitAdmin.declareQueue(businessQueueB());
        return rabbitAdmin;
    }


}
