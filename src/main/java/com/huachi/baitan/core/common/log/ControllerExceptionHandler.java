/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.common.log;

import java.io.IOException;
import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSON;
import com.huachi.baitan.core.common.constants.SharkConstants;
import com.huachi.baitan.core.common.filter.WebRequestLogFilter;
import com.huachi.baitan.core.common.log.annotation.EnableAutoLogMethod;
import com.huachi.baitan.core.common.support.EmptyBodyCheckingHttpInputMessage;

/**
 * 类ControllerExceptionHandler的实现描述：全局Controller异常处理器
 * <p>
 * Spring Boot官方文档给出的示例是直接继承{@link ResponseEntityExceptionHandler}，但是此类中的方法
 * {@link ResponseEntityExceptionHandler#handleException(Exception, WebRequest)}
 * 是final的， 无法进行覆盖 （不覆盖将会导致此方法上捕获的异常无法在使用AOP拦截），并且没有将异常输出到log中。通过重写方法
 * {@link ResponseEntityExceptionHandler#handleExceptionInternal(Exception, Object, HttpHeaders, HttpStatus, WebRequest)}
 * ，因为所有的异常处理最后一步都会走到这里，对外的错误异常统一输出，完美解决。
 * <p>
 * 如果要开启此功能，{@link EnableAutoLogMethod}
 * <p>
 * 如果要禁用掉此功能，{@link EnableAutoLogMethod#enableLogControllerException()}
 *
 * @see ResponseEntityExceptionHandler
 * @see EnableAutoLogMethod
 */
@ConditionalOnWebApplication
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    public static HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

    /**
     * 不在页面暴露具体的异常信息
     */
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity<Object> handleException(Throwable ex, HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        return outputException(ex, new HttpHeaders(), status, request);
    }

    /**
     * {@link ResponseEntityExceptionHandler#handleException(Exception, WebRequest)}
     * 是final的， 无法进行覆盖 （不覆盖将会导致此方法上捕获的异常无法在使用AOP拦截），并且没有将异常输出到log中。通过重写方法
     * {@link ResponseEntityExceptionHandler#handleExceptionInternal(Exception, Object, HttpHeaders, HttpStatus, WebRequest)}
     * ，因为所有的异常处理最后一步都会走到这里，对外的错误异常统一输出，完美解决。
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object responseBody, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
        return outputException(ex, headers, status, servletRequest);
    }

    /**
     * 对外的错误异常统一输出JSON字符串，并且包含logId，方便跟踪错误日志
     */
    protected ResponseEntity<Object> outputException(Throwable ex, HttpHeaders headers, HttpStatus status,
                                                     HttpServletRequest servletRequest) {
        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(servletRequest);
        URI requestURI = serverHttpRequest.getURI();
        String requestBody = null;
        try {
            EmptyBodyCheckingHttpInputMessage inputMessage = new EmptyBodyCheckingHttpInputMessage(serverHttpRequest);
            if (inputMessage.getBody() != null) {
                requestBody = StreamUtils.copyToString(inputMessage.getBody(), SharkConstants.DEFAULT_CHARSET);
            } else if (!CollectionUtils.isEmpty(servletRequest.getParameterMap())) {
                requestBody = JSON.toJSONString(servletRequest.getParameterMap());
            }
        } catch (IOException e) {
            log.error("Could not read document", e);
        }
        if (ex instanceof RestClientResponseException) {
            RestClientResponseException exception = (RestClientResponseException) ex;
            String responseBody = exception.getResponseBodyAsString(); // 打印出发送http请求的错误信息，帮助追踪错误源
            log.error(
                    "requestUrl: {}, requestMethod: {}, requestBody: {}, contentType: {}\norg.springframework.web.client.RestClientResponseException: {}, responseBody: {} ",
                    requestURI, serverHttpRequest.getMethod(), requestBody, servletRequest.getContentType(),
                    exception.getMessage(), responseBody, ex);
        } else {
            log.error("requestUrl: {}, requestMethod: {}, requestBody: {}, contentType: {}", requestURI,
                    serverHttpRequest.getMethod(), requestBody, servletRequest.getContentType(), ex);
        }
        Object body = "{\"msg\":\"We'll be back soon ...\", \"logId\":\"" + WebRequestLogFilter.getLogId() + "\"}";
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return ResponseEntity.status(status).headers(headers).body(body);
    }
}
