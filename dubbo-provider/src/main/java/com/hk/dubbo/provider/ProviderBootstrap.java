package com.hk.dubbo.provider;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author : HK意境
 * @ClassName : ProviderBootstrap
 * @date : 2022/4/9 13:24
 * @description : dubbo 服务提供应用
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@EnableDubboConfiguration
@SpringBootApplication
public class ProviderBootstrap {

    public static void main(String[] args) {

        SpringApplication.run(ProviderBootstrap.class,args);

    }


}
