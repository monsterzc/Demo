package com.can;

import cn.easyes.starter.register.EsMapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



/**
 * @Description 启动类
 * @Author zhengcan
 * @Date 2023/4/4 16:25
 * @Version 1.0
 */
@EsMapperScan("com.can.mapper")
@SpringBootApplication
public class SpringbootElasticsearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootElasticsearchApplication.class, args);
    }

}
