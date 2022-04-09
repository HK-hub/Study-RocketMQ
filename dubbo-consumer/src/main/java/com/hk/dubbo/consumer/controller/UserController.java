package com.hk.dubbo.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hk.mq.shop.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : HK意境
 * @ClassName : UserController
 * @date : 2022/4/9 14:32
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService ;

    @RequestMapping("/say")
    public String sayHello(@RequestParam("name") String name){


        return userService.sayHello(name);
    }


}
