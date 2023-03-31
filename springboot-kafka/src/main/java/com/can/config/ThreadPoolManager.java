package com.can.config;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * @className: ThreadPoolManager
 * @description: 线程管理
 * @author: zhengcan
 * @date: 2023/3/31
 **/
@Slf4j
@Component
public class ThreadPoolManager {

    public static Map<String, Consumer<Object>> consumerMap = new HashMap<>();


    /**
     * 最小线程数（核心线程数）
     */
    private static int SIZE_CORE_POOL = 20;

    /**
     * 最大线程数
     */
    private static int SIZE_MAX_POOL = 20;

    /**
     * 线程空闲存活时间
     */
    private static int TIME_KEEP_ALIVE = 5000;

    /**
     * 缓冲队列大小
     */
    private static int SIZE_WORK_QUEUE = 1;

    /**
     * 源子类进行拒绝策略计数
     */
    public static AtomicInteger number = new AtomicInteger();

    /**
     * 计数器，给main线程判断线程池是否执行完
     */
    public static final CountDownLatch COUNT = new CountDownLatch(600);


    /**
     * 自定义拒绝策略，将超过线程数量的任务放入缓冲队列
     */
    private static final RejectedExecutionHandler mHandle = new RejectedExecutionHandler() {

        @SneakyThrows
        @Override
        public void rejectedExecution(Runnable task, ThreadPoolExecutor executor) {
            number.incrementAndGet();
            mThreadPool.getQueue().put(task);
            log.info("进入缓冲队列");
        }
    };

    private static ThreadPoolExecutor mThreadPool;
    /**
     * 线程初始化
     *
     */
    @PostConstruct
    public static void init(){
        log.info(">>>>>>>>>>>>>线程池初始化");
        mThreadPool = new ThreadPoolExecutor(
                SIZE_CORE_POOL,
                SIZE_MAX_POOL,
                TIME_KEEP_ALIVE,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(SIZE_WORK_QUEUE),
                Executors.defaultThreadFactory(),
                mHandle
        );
    }
    /**
     * 根据不同的type进行不同的方法，利用Java的consumer类型
     * @param type
     * @param data
     */
    public static void addExecuteTask(String type, Object data) {
        try {
            mThreadPool.execute(()->{
                try {
                    consumerMap.get(type).accept(data);
                } catch (Exception e) {
                    log.error("线程池执行任务失败");
                }
            });
        } finally {
            COUNT.countDown();
        }
    }
}
