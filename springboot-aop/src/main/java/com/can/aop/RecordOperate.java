package com.can.aop;

import java.lang.annotation.*;

/**
 * @className: RecordOperate
 * @description: RecordOperate
 * @author: zhengcan
 * @date: 2023/4/2
 **/

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RecordOperate {

    String desc() default "";

    Class<? extends Convert> convert() ;
}
