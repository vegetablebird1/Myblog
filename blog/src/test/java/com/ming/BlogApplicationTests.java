package com.ming;

import cn.hutool.crypto.SecureUtil;
import com.ming.entity.User;
import com.ming.mapper.UserMapper;
import com.ming.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
        System.out.println(userService.list());
    }

    @Test
    void test01(){
        System.out.println(SecureUtil.md5("123456").equals("e10adc3949ba59abbe56e057f20f883e"));
    }

    @Test
    void test02(){
        User user = new User();
        user.setUsername("121");
        user.setPassword("123");
        userService.saveOrUpdate(user);
    }
}
