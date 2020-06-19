/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.common.wechat.dto;

import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import com.alibaba.fastjson.annotation.JSONField;
import com.huachi.baitan.core.common.utils.FastJsonUtils;

/**
 * 类WeChatUserInfo的实现描述：微信用户信息
 */
@Data
public class WeChatUserInfo {
    private String    openId;
    private String    nickName;
    private String    gender;
    private String    city;
    private String    province;
    private String    country;
    private String    avatarUrl;
    private String    unionId;
    private Watermark watermark;

    @Getter
    @Setter
    public class Watermark {
        @JSONField(serialize = false, name = "appid")
        private String appId;
        @JSONField(serialize = false)
        private Long   timestamp;
        private Date   time;

        public void setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
            this.time = new Date(timestamp * 1000);
        }
    }

    @Override
    public String toString() {
        return FastJsonUtils.toJSONString(this);
    }
}
