/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.user.controller;

import lombok.extern.slf4j.Slf4j;

import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.huachi.baitan.core.common.Result;
import com.huachi.baitan.core.common.TransformDTO;
import com.huachi.baitan.core.common.constants.SharkCodeMsgEnum;
import com.huachi.baitan.core.common.log.annotation.LogMethodAround;
import com.huachi.baitan.core.common.utils.WeChatUtils;
import com.huachi.baitan.core.common.wechat.dto.WeChatLoginSession;
import com.huachi.baitan.core.common.wechat.servlet.mvc.method.annation.WeChatUserSession;
import com.huachi.baitan.core.user.dto.UserPhoneDO;
import com.huachi.baitan.core.user.service.UserService;

/**
 * 类UserController的实现描述：用户注册登录维护类
 *
 * @author weiliting 2020年6月14日 PM3:30:26
 */
@Slf4j
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
    public Result<Boolean> register(@RequestBody UserPhoneDO params, @WeChatUserSession WeChatLoginSession loginSession) {
        Result<Boolean> result = new Result<>();
        String phone = params.getPhone();
        if (params.getChannelType() == null || StringUtils.isBlank(phone)) {
            return TransformDTO.setErrorInfo(result, SharkCodeMsgEnum.VALUE_IS_INVALID.getCode(),
                    "channelType, phone must not be null");
        }

        String phoneJson = "";
        try {
            phoneJson = WeChatUtils.decryptedInfo(phone, loginSession.getSessionKey(), params.getIv());
        } catch (Exception ex) {
            log.warn("decryptedUserInfo failed", ex);
        }
        if (StringUtils.isBlank(phoneJson)) {
            return TransformDTO.setErrorInfo(result, SharkCodeMsgEnum.VALUE_IS_INVALID.getCode(), "解密失败");
        }
        JSONObject jsonObject = JSONObject.parseObject(phoneJson);
        phone = jsonObject.getString("phoneNumber");
        if (StringUtils.isBlank(phone)) {
            return TransformDTO.setErrorInfo(result, SharkCodeMsgEnum.VALUE_IS_INVALID.getCode(), "解密失败");
        }
        params.setPhone(phone);
        Result<Boolean> saveResult = userService.register(params, loginSession);
        if (saveResult.isSuccess() && params.isNeedReturnPhone()) {
            saveResult.addExtraInfo("phone", phone);
        }
        return saveResult;
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
