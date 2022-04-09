package com.hk.dubbo.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.hk.mq.shop.UserService;
import org.springframework.stereotype.Component;

/**
 * @author : HK意境
 * @ClassName : UserService
 * @date : 2022/4/9 14:27
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Component
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {
    @Override
    public String sayHello(String name) {

        return "hello: " + name ;
    }
}
