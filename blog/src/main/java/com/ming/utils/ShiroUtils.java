package com.ming.utils;

import com.ming.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

public class ShiroUtils {

    /**
     * 获得用户
     * @return
     */
    public static AccountProfile getProfile(){
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }
}
