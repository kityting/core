/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.common.wechat.servlet.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.AllArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.alibaba.fastjson.JSONObject;
import com.huachi.baitan.core.common.constants.SharkConstants;
import com.huachi.baitan.core.common.utils.RedisUtils;
import com.huachi.baitan.core.common.wechat.dto.WeChatLoginSession;

/**
 * 类WeChatAppCheckSessionFilter的实现描述：微信小程序校验用户登陆状态有效性
 */
@AllArgsConstructor
public class WeChatAppCheckSessionFilter extends OncePerRequestFilter {
    @Resource
    private RedisUtils redisUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String sessionKey = request.getHeader("Session-Key");
        boolean flag = StringUtils.isNotBlank(sessionKey);
        if (flag) {
            // 校验用户登陆状态是否有效
            String session = String.valueOf(redisUtils.get(sessionKey));
            WeChatLoginSession loginSession = JSONObject.parseObject(session, WeChatLoginSession.class);
            if (loginSession == null || StringUtils.isBlank(loginSession.getOpenid())
                    || StringUtils.isBlank(loginSession.getSessionKey())) {
                flag = false;
            } else {
                request.setAttribute(SharkConstants.WE_CHAT_APP_USER_SESSION, loginSession);
            }
        }
        if (flag) {
            filterChain.doFilter(request, response);
        } else {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(403);
            PrintWriter writer = response.getWriter();
            writer.write("{\"errorCode\":\"08\",\"errorMsg\":\"illegal request\",\"success\":false}");
            writer.close();
        }
    }
}
