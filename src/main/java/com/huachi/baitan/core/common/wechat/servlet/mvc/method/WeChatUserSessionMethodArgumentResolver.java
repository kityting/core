/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.common.wechat.servlet.mvc.method;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.huachi.baitan.core.common.wechat.servlet.mvc.method.annation.WeChatUserSession;

/**
 * 类WeChatUserSessionMethodArgumentResolver的实现描述：spring mvc controller 方法中的
 * {@link WeChatUserSession} 参数映射
 */
public class WeChatUserSessionMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(WeChatUserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        WeChatUserSession ann = parameter.getParameterAnnotation(WeChatUserSession.class);
        String sessionKey = StringUtils.isBlank(ann.value()) ? ann.sessionKey() : ann.value();
        return request.getAttribute(sessionKey);
    }
}
