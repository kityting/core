/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.common.log;

import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 类ExceptionMonitorLogger的实现描述：错误信息监控日志记录
 */
@Slf4j(topic = "MonitorException")
@Aspect
//不启用此配置，比如：client请求 -> controller -> service -> dao，假设 dao 中出现异常，那么 dao -> service -> controller 都会记录一次异常信息
//@Component
public class MonitorExceptionLog {

    /**
     * 排除哪些方法
     * <ul>
     * <li>排除方法上有@ExceptionHandler注解，Controller的异常处理:
     * <code>@org.springframework.web.bind.annotation.ExceptionHandler</code>
     * <li>排除方法上有@Scheduled注解，Spring Task定时任务:
     * <code>@org.springframework.scheduling.annotation.Scheduled</code>
     * </ul>
     *
     * @see org.springframework.aop.aspectj.AspectJExpressionPointcut#matches(Method,
     *      Class, boolean)
     */
    @Pointcut("execution(@org.springframework.web.bind.annotation.ExceptionHandler * *(..))"
            + " || execution(@org.springframework.scheduling.annotation.Scheduled * *(..))")
    public void unWantToMatch() {
        // Do nothing
    }

    /**
     * 监控包下的非private/protected/final的方法，非AOP调用的方法也是不支持的
     */
    @Pointcut("execution(* com.huachi.*.*(..))")
    public void wantToMatch() {
        // Do nothing
    }

    @Pointcut("wantToMatch() && ! unWantToMatch()")
    public void allWantToMatch() {
        // Do nothing
    }

    @AfterThrowing(value = "allWantToMatch()", throwing = "throwable")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable throwable) {
        String methodName = joinPoint.getSignature().toString();
        log.error("execute method [" + methodName + "] throw exception", throwable);
    }

}
