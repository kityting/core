/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.spring.config;

import org.springframework.context.annotation.Configuration;

import com.huachi.baitan.core.common.log.annotation.EnableAutoLogMethod;

/**
 * 类WebConfig的实现描述：web的bean初始化配置
 *
 * @author weiliting 2020年6月18日 PM12:27:03
 */
@EnableAutoLogMethod
@Configuration
public class WebConfig extends WebAppMvcConfig {
}
