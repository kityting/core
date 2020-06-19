/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.common.wechat.servlet.mvc.method.annation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

import com.huachi.baitan.core.common.constants.SharkConstants;
import com.huachi.baitan.core.common.wechat.dto.WeChatLoginSession;
import com.huachi.baitan.core.common.wechat.servlet.filter.WeChatAppCheckSessionFilter;

/**
 * 类WeChatUserSession的实现描述：微信小程序用户登陆Session信息
 * 
 * @see WeChatLoginSession
 * @see WeChatAppCheckSessionFilter
 */
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WeChatUserSession {
    /**
     * Alias for {@link #sessionKey}.
     */
    @AliasFor("sessionKey")
    String value() default SharkConstants.WE_CHAT_APP_USER_SESSION;

    @AliasFor("value")
    String sessionKey() default SharkConstants.WE_CHAT_APP_USER_SESSION;
}
