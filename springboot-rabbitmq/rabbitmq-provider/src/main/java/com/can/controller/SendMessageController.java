package com.can.controller;

import com.can.config.DeadLetterRabbitConfig;
import com.can.config.DelayRabbitMq;
import com.can.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @className: SendMessageController
 * @description: 发消息控制类
 * @author: zhengcan
 * @date: 2023/3/30
 **/
@Slf4j
@RestController
//@RequestMapping("/send")
public class SendMessageController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/sendDirectMessage")
    public String sendDirectMessage(){
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "test message, hello can!";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", map);
        return "ok";

    }

    @GetMapping("/TestMessageAck")
    public String TestMessageAck(){
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: non-existent-exchange test message";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        HashMap<String, Object> map = new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend("non-existent-exchange","TestDirectRouting",map);
        return "ok";
    }

    @GetMapping("/TestMessageAck2")
    public String TestMessageAck2() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: lonelyDirectExchange test message ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend("lonelyDirectExchange", "TestDirectRouting", map);
        return "ok";
    }

    @GetMapping("/sendTopicMessage1")
    public String sendTopicMessage1(){
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: M A N";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss"));
        HashMap<String,Object> manMap = new HashMap<>();
        manMap.put("messageId", messageId);
        manMap.put("messageData",messageData);
        manMap.put("createTime",createTime);
        rabbitTemplate.convertAndSend("topicExchange","topic.man",manMap);
        return "ok";
    }

    @GetMapping("/sendTopicMessage2")
    public String sendTopicMessage2(){
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: woman is all";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss"));
        HashMap<String,Object> womanMap = new HashMap<>();
        womanMap.put("messageId", messageId);
        womanMap.put("messageData",messageData);
        womanMap.put("createTime",createTime);
        rabbitTemplate.convertAndSend("topicExchange","topic.woman",womanMap);
        return "ok";
    }

    @GetMapping("/sendFanoutMessage")
    public String sendFanoutMessage(){
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: testFanoutMessage";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss"));
        HashMap<String,Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);
        rabbitTemplate.convertAndSend("fanoutExchange",null,map);
        return "ok";
    }

    @GetMapping("/sendDeadLetterMessage")
    public String sendDeadLetterMessage(){
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: dead.letter.business.exchange test message";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss"));
        HashMap<String,Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);
        map.put("type","死信测试");
        rabbitTemplate.convertAndSend(Constant.BUSINESS_EXCHANGE,Constant.BUSINESS_QUEUE_A_ROUTING_KEY,map);
        return "ok";
    }

    @GetMapping("/sendDeadLetterMessage3")
    public String sendDeadLetterMessage3(){
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: dead.letter.business.exchange test message";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss"));
        HashMap<String,Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);
        map.put("type","死信测试");
        rabbitTemplate.convertAndSend(Constant.BUSINESS_EXCHANGE,Constant.BUSINESS_QUEUE_B_ROUTING_KEY,map);
        return "ok";
    }

    @GetMapping("/sendDeadLetterMessage2")
    public String sendDeadLetterMessage2(String msg){
        String messageId = String.valueOf(UUID.randomUUID());
//        String messageData = "七里香 DirectMessage";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        HashMap<String, Object> map = new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",msg);
        map.put("createTime",createTime);
        map.put("type","死信测试");
        //将消息主题：topic.man 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend(DeadLetterRabbitConfig.EXCHANGE,DeadLetterRabbitConfig.ROUTING_KEY,map);
        return "发送成功";
    }

    @GetMapping("/sendDelayMessage")
    public String sendDelayMessage(Integer delayTime){
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message:delay.exchange test message";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        HashMap<String, Object> map = new HashMap<>(4);
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        map.put("type", "延时队列测试");
        // 将主题消息发送到延时队列交换机
        rabbitTemplate.convertAndSend(DelayRabbitMq.DELAY_EXCHANGE,DelayRabbitMq.DELAY_ROUTING_KEY_A,map,msg->{
            msg.getMessageProperties().setDelay(delayTime);
            return msg;
        });
        return "发送成功";
    }


    @GetMapping("delayMsg")
    public void delayMsg(String msg, Integer delayTime) {
        log.info("当前时间：{},收到请求，msg:{},delayTime:{}", new Date(), msg, delayTime);
        rabbitTemplate.convertAndSend(DelayRabbitMq.DELAY_EXCHANGE,DelayRabbitMq.DELAY_ROUTING_KEY_B,msg,a->{
            a.getMessageProperties().setDelay(delayTime);
            return a;
        });
    }
}
