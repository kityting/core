/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.common.wechat.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 类WeChatLoginSession的实现描述：微信Session信息DTO
 */
@Getter
@Setter
public class WeChatLoginSession {
    /**
     * 所属渠道类型
     */
    private Integer             channelType;
    /**
     * 用户标示id，加密后的open_user_id，可以直接暴露给客户端
     */
    private String              openUserIdEncrypt;
    /**
     * 用户唯一标识
     */
    private String              openid;
    /**
     * 会话密钥
     */
    @JSONField(name = "session_key")
    private String              sessionKey;
    /**
     * 过期时间
     */
    @JSONField(name = "expires_in")
    private Integer             expiresIn;
    /**
     * 错误代码
     */
    @JSONField(name = "errcode")
    private String              errorCode;
    /**
     * 错误描述
     */
    @JSONField(name = "errmsg")
    private String              errorMsg;
    /**
     * 用户unionId（扩展字段）
     */
    private String              unionId;
    /**
     * 扩展信息
     */
    private Map<String, Object> extraInfo;

    /**
     * 增加扩展信息
     *
     * @param key 扩展信息中的key
     * @param value 扩展信息中的value
     */
    public WeChatLoginSession addExtraInfo(String key, Object value) {
        if (this.extraInfo == null) {
            this.extraInfo = new HashMap<>();
        }
        this.extraInfo.put(key, value);
        return this;
    }

    /**
     * 获取扩展信息
     *
     * @param key 扩展信息中的key
     * @return 扩展信息中的value
     */
    public Object getExtraInfo(String key) {
        return this.extraInfo == null ? null : this.extraInfo.get(key);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
