/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.common.wechat.config;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * 类WeChatAutoConfiguration的实现描述：自动配置微信小程序
 */
@Slf4j
@EnableCaching
@EnableConfigurationProperties(WeChatAppProperties.class)
@ConditionalOnProperty(prefix = "shark.wechat.app", value = "enabled", matchIfMissing = false)
@Configuration
public class WeChatAutoConfiguration {
}
