package com.ming.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.ming.entity.User;
import com.ming.mapper.UserMapper;
import com.ming.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ming
 * @since 2021-05-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public void register(User user) {
        String password = SecureUtil.md5(user.getPassword());
        user.setPassword(password);
        userMapper.insert(user);
    }
}
