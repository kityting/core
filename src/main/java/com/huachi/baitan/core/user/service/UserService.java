/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.user.service;

import com.alibaba.fastjson.JSONObject;
import com.huachi.baitan.core.common.Result;
import com.huachi.baitan.core.common.wechat.dto.WeChatLoginSession;
import com.huachi.baitan.core.user.dto.UserPhoneDO;

/**
 * 类UserService的实现描述：用户服务接口
 *
 * @author weiliting 2020年6月17日 PM2:21:56
 */
public interface UserService {
    /**
     * 新用户注册
     * 
     * @param params 用户信息
     * @param loginSession 用户登录信息
     * @return 成功与否
     */
    Result<Boolean> register(UserPhoneDO params, WeChatLoginSession loginSession);

    /**
     * 用户登录
     *
     * @param params 参数
     * @return 登录唯一标识
     */
    Result<String> login(JSONObject params);
}
