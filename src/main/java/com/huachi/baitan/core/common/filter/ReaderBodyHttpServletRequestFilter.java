/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.common.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.huachi.baitan.core.common.support.ReaderBodyHttpServletRequestWrapper;

/**
 * 类ReaderBodyHttpServletRequestFilter的实现描述：将{@link HttpServletRequest}进行包装为
 * {@link ReaderBodyHttpServletRequestWrapper}，使下面这两个方法支持多次读取，默认只能读取一次
 * <ul>
 * <li> {@link ReaderBodyHttpServletRequestWrapper#getReader()}
 * <li> {@link ReaderBodyHttpServletRequestWrapper#getInputStream()}
 * </ul>
 *
 * @see ReaderBodyHttpServletRequestWrapper
 */
public class ReaderBodyHttpServletRequestFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ServletRequest requestWrapper = new ReaderBodyHttpServletRequestWrapper(request);
        filterChain.doFilter(requestWrapper, response);
    }
}
