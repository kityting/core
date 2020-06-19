/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.common.wechat.config;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 类WeChatAppProperties的实现描述：微信小程序的配置
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "shark.wechat.app")
public class WeChatAppProperties {
    /**
     * 微信小程序的配置，可以设置多个
     */
    private Map<String, WeChatApp> configs;

    @Getter
    @Setter
    public static class WeChatApp {
        /**
         * 小程序唯一标识，微信分配的小程序ID
         */
        private String appId;
        /**
         * 小程序的 app secret，高级调用功能的密钥
         */
        @JSONField(serialize = false)
        private String secret;
        /**
         * 微信支付分配的商户号
         */
        @JSONField(serialize = false)
        private String mchId;
        /**
         * 加密key，key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
         */
        @JSONField(serialize = false)
        private String signKey;

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }

    public WeChatApp getByAppId(String appId) {
        for (WeChatApp weChatApp : configs.values()) {
            if (appId.equals(weChatApp.getAppId())) {
                return weChatApp;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
