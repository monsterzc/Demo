package com.can.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @className: OperateAspect
 * @description: 切面类
 * @author: zhengcan
 * @date: 2023/4/2
 **/
@Slf4j
@Component
@Aspect
@EnableAspectJAutoProxy
public class OperateAspect {

    /**
     * 1.定义切点
     * 2.横切逻辑
     * 3.织入
     */

    @Pointcut("@annotation(com.can.aop.RecordOperate)")
    public void pointcut(){}

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            1, 1, 1, TimeUnit.SECONDS, new LinkedBlockingDeque<>(100)
    );

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 拿到反射结果
        Object result = proceedingJoinPoint.proceed();

        threadPoolExecutor.execute(() -> {
            try {
                MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
                RecordOperate annotation = methodSignature.getMethod().getAnnotation(RecordOperate.class);

                Class<? extends Convert> convert = annotation.convert();
                Convert logConvert = convert.newInstance();
                OperateLogDo operateLogDo = logConvert.convert(proceedingJoinPoint.getArgs()[0]);

                operateLogDo.setDesc(annotation.desc());
                operateLogDo.setRecord(result.toString());

                log.info("insert operateLog:{} ", JSON.toJSONString(operateLogDo));
            } catch (InstantiationException e) {
                throw new RuntimeException();
            } catch (IllegalAccessException e) {
                throw new RuntimeException();
            }
        });

        return result;
    }

}
