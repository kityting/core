/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.user.service.impl;

import javax.annotation.Resource;

import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huachi.baitan.core.common.Result;
import com.huachi.baitan.core.common.utils.RedisUtils;
import com.huachi.baitan.core.user.dao.UserDao;
import com.huachi.baitan.core.user.entity.User;
import com.huachi.baitan.core.user.service.UserService;

/**
 * 类UserServiceImpl的实现描述：用户服务实现类
 *
 * @author weiliting 2020年6月17日 PM2:22:17
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao    userDao;
    @Resource
    private RedisUtils redisUtils;

    @Override
    public Result<Boolean> register(User user) {
        Result<Boolean> result = new Result<>(false);
        if (StringUtils.isBlank(user.getPhone())) {
            result.setErrorCode("500");
            result.setErrorMsg("phone must be not blank");
        }
        Integer insertResult = userDao.insert(user);
        if (insertResult > 0) {
            result.setSuccess(true);
            result.setValue(true);
        }
        return result;
    }

    @Override
    public Result<String> login() {
        Result<String> result = new Result<>();

        return result;
    }
}
