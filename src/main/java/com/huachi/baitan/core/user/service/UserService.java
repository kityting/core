/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.user.service;

import com.huachi.baitan.core.common.Result;
import com.huachi.baitan.core.user.entity.User;

/**
 * 类UserService的实现描述：用户服务接口
 *
 * @author weiliting 2020年6月17日 PM2:21:56
 */
public interface UserService {
    /**
     * 新用户注册
     * 
     * @param user 用户信息
     * @return 成功与否
     */
    Result<Boolean> register(User user);

    Result<String> login();
}
