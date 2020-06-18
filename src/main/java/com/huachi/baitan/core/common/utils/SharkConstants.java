/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.common.utils;

import java.nio.charset.Charset;

/**
 * 类SharkConstants的实现描述：shark项目常量
 */
public interface SharkConstants {
    /**
     * system用户
     */
    String  USER_SYSTEM              = "system";

    /**
     * 常量: Y
     */
    String  Y                        = "Y";
    /**
     * 常量: N
     */
    String  N                        = "N";

    /**
     * 分布式缓存的cacheName
     */
    String  DISTRIBUTED_CACHE        = "distributedCache";
    /**
     * 内存缓存的cacheName
     */
    String  MEMORY_CACHE             = "memoryCache";

    /**
     * 基于PowerCache的中央缓存
     */
    String  POWER_CACHE              = "powerCache";

    /**
     * 用于LOG使用Appender输出日志的时候，在pattern中配置<code>%X{logId}</code>的key
     */
    String  LOG_ID                   = "logId";
    /**
     * UTF-8: 默认的字符集
     */
    Charset DEFAULT_CHARSET          = Charset.forName("UTF-8");
    /**
     * 部署的环境变量（dev,test,pre,prd）
     */
    String  DEPLOY_ENV               = "DEPLOY_ENV";

    /**
     * 微信小程序登陆信息key
     */
    String  WE_CHAT_APP_USER_SESSION = "FLYING_SHARK_WE_CHAT_APP_USER_SESSION";

    /**
     * 众安App、H5页面登陆信息key
     */
    String  ZA_APP_USER_SESSION      = "FLYING_SHARK_ZA_APP_USER_SESSION";

    /**
     * App类别：航旅类
     */
    String  APP_CATEGORY_AIR_TRAVEL  = "airTravel";
    /**
     * App类别：商险类
     */
    String  APP_CATEGORY_COMMERCE    = "commerce";
}
