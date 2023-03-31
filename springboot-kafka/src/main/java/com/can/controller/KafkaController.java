package com.can.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.can.config.KafkaConfig;
import com.can.config.KafkaProducer;
import com.can.config.ThreadPoolManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * @className: KafkaController
 * @description: TODO 类描述
 * @author: zhengcan
 * @date: 2023/3/31
 **/
@Slf4j
@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @GetMapping("/test")
    public String test() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6);
            String parse = JSON.toJSONString(integers);
            kafkaProducer.send(parse);
        }
        ThreadPoolManager.COUNT.await();
        log.info(">>>>>>>,进入缓冲队列的次数:{}",ThreadPoolManager.number);
        ThreadPoolManager.number.set(0);
        return "success";
    }

    @PostMapping("/send/{msg}")
    public String sendMsg(@PathVariable("msg")String msg) throws ExecutionException, InterruptedException {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(KafkaConfig.TOPIC_NAME, msg);
        log.info("发送结果：{}",future.get().toString());
        return "发送成功";
    }

    @PostMapping("/send/syn/{msg}")
    public String sendMsgSyn(@PathVariable("msg")String msg){
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(KafkaConfig.TOPIC_NAME, msg);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("发送异常");
                ex.printStackTrace();
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("发送成功 result ="+result);
            }
        });
        return "发送成功";
    }

    /**
     * 增加事务，保证Kafka多次发送的原子性，要么都成功，要么都失败
     */
    @PostMapping("/send/ts/{msg}")
    @Transactional(rollbackFor = RuntimeException.class)
    public String sendMsgTs(@PathVariable("msg") String msg){
        kafkaTemplate.send(KafkaConfig.TOPIC_NAME,msg);
        if (msg.length() > 5) {
            kafkaTemplate.send(KafkaConfig.TOPIC_NAME, msg);
        }else {
            throw new RuntimeException("演示异常");
        }
        return "发送成功";

    }

    /**
     * 事务提交
     * @param msg
     * @return String
     */
    @PostMapping("/send/ts2/{msg}")
    // @Transactional 验证非事务方法提交
    public String sendMsgTs2(@PathVariable("msg") String msg) {
        //kafkaTemplate.send(KafkaConfig.TOPIC_NAME,"非事务提交");验证非事务方法提交
        Boolean result = kafkaTemplate.executeInTransaction(kafkaOperations -> {
            kafkaOperations.send(KafkaConfig.TOPIC_NAME,msg);
            throw new RuntimeException("fail");
        });
        assert result != null;
        return result.toString();
    }
}
