/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.common.log.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.huachi.baitan.core.common.log.AspectJAutoLogMethodRegistrar;
import com.huachi.baitan.core.common.log.ControllerExceptionHandler;

/**
 * 类EnableLogMethod的实现描述：开启下面几个注解的功能，使用slf4j记录方法的入参和出参；同时支持开启自动记录 controller 异常
 * <ul>
 * <li>{@link LogMethodBefore}
 * <li>{@link LogMethodAfter}
 * <li>{@link LogMethodAround}
 * <li>{@link LogUnMonitor}
 * <li>{@link ControllerExceptionHandler}
 * </ul>
 * 使用例子：
 *
 * <pre>
 * &#064;EnableAutoLogMethod
 * &#064;Configuration
 * public class Config {
 * }
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(AspectJAutoLogMethodRegistrar.class)
public @interface EnableAutoLogMethod {
    /**
     * 开启自动记录 method 的入参和出参
     * <ul>
     * <li>{@link LogMethodBefore}
     * <li>{@link LogMethodAfter}
     * <li>{@link LogMethodAround}
     * <li>{@link LogUnMonitor}
     * </ul>
     */
    boolean enableLogMethod() default true;

    /**
     * 开启自动记录 controller 异常
     *
     * @return 默认开启（如果不是WebApplication，是不起作用的）
     * @see ControllerExceptionHandler
     */
    boolean enableLogControllerException() default true;
}
