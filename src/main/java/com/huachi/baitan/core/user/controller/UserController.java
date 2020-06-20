/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.huachi.baitan.core.common.Result;
import com.huachi.baitan.core.common.log.annotation.LogMethodAround;
import com.huachi.baitan.core.user.entity.User;
import com.huachi.baitan.core.user.service.UserService;

/**
 * 类UserController的实现描述：用户注册登录维护类
 *
 * @author weiliting 2020年6月14日 PM3:30:26
 */
@LogMethodAround
@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 注册接口
     */
    @RequestMapping(value = "/register")
    public Result<Boolean> register(@RequestBody User user) {
        return userService.register(user);
    }

    /**
     * 登录接口
     * 
     * @param params JSONObject
     * @return 登录唯一标识
     */
    @PostMapping("/login")
    public Result<String> login(@RequestBody JSONObject params) {
        return userService.login(params);
    }
}
