package com.ming.service;

import com.ming.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ming
 * @since 2021-05-05
 */
public interface UserService extends IService<User> {

    void register(User user);
}
