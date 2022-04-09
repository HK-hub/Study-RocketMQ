package com.hk.dubbo.consumer;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author : HK意境
 * @ClassName : ConsumerBootstrap
 * @date : 2022/4/9 14:29
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */

@EnableDubboConfiguration
@SpringBootApplication
public class ConsumerBootstrap {

    public static void main(String[] args) {

        SpringApplication.run(ConsumerBootstrap.class,args);

    }


}
