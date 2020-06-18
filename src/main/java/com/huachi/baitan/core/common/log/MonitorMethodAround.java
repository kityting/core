/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.common.log;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.huachi.baitan.core.common.log.annotation.MonitorMethodAdvice;

/**
 * 类MonitorLogMethodAround的实现描述：记录方法的入参和返回值，使用注解： {@link}
 * <p>
 * 非private/final的方法，非AOP调用的方法也是不支持的
 */
@Slf4j(topic = "MonitorMethod")
@Aspect
@MonitorMethodAdvice
public class MonitorMethodAround extends AbstractMonitorLog {

    /**
     * 排除哪些方法
     * <ul>
     * <li>排除方法上有@ExceptionHandler注解，Controller的异常处理:
     * <code>@org.springframework.web.bind.annotation.ExceptionHandler</code>
     * <li>排除方法上有@Scheduled注解，Spring Task定时任务:
     * <code>@org.springframework.scheduling.annotation.Scheduled</code>
     * <li>排除方法、类上有@LogUnMonitor注解，自定义Log注解:
     * <code>@com.zhongan.flying.shark.log.annotation.LogUnMonitor</code>
     * </ul>
     *
     * @see org.springframework.aop.aspectj.AspectJExpressionPointcut#matches(Method,
     *      Class, boolean)
     */
    @Pointcut("execution(@org.springframework.web.bind.annotation.ExceptionHandler * *(..))"
            + " || execution(@org.springframework.scheduling.annotation.Scheduled * *(..))"
            + " || @within(com.huachi.baitan.core.common.log.annotation.LogUnMonitor)" // 放在class上有效，method上无效
            + " || @annotation(com.huachi.baitan.core.common.log.annotation.LogUnMonitor)" // 放在class上无效，method上有效
    )
    public void unWantToMatch() {
        // Do nothing
    }

    /**
     * 监控哪些方法
     * <ul>
     * <li>方法、类上有@LogMethodAround，自定义Log注解:
     * <code>@com.zhongan.flying.shark.log.annotation.LogMethodAround</code>
     * </ul>
     */
    @Pointcut("@within(com.huachi.baitan.core.common.log.annotation.LogMethodAround)"// 放在class上有效，method上无效
            + " || @annotation(com.huachi.baitan.core.common.log.annotation.LogMethodAround)" // 放在class上无效，method上有效
    )
    public void wantToMatch() {
        // Do nothing
    }

    @Pointcut("wantToMatch() && ! unWantToMatch()")
    public void allWantToMatch() {
        // Do nothing
    }

    @Around("allWantToMatch()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTimeMillis = System.currentTimeMillis();
        long start = System.nanoTime();
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getDeclaringTypeName() + "#" + signature.getName();
        Logger logger = LoggerFactory.getLogger(methodName);
        JSONObject jsonBefore = new JSONObject();
        jsonBefore.put("start", startTimeMillis);
        jsonBefore.put("args", convertArgs(joinPoint.getArgs()));
        jsonBefore.put("app", appName);
        logger.info(toJSONString(jsonBefore));

        // 这里不要去做： try/catch, A catch statement should never catch throwable since it includes errors
        Object result = joinPoint.proceed();

        long costTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
        JSONObject jsonAfter = new JSONObject();
        jsonAfter.put("start", startTimeMillis);
        jsonAfter.put("end", System.currentTimeMillis());
        jsonAfter.put("cost", costTime);
        jsonAfter.put("result", result);
        jsonAfter.put("app", appName);
        logger.info(toJSONString(jsonAfter));

        return result;
    }
}
