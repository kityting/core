/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.common.log;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ClassUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 类AbstractMonitorLog的实现描述：记录日志的基类
 */
public abstract class AbstractMonitorLog {
    @Value("${shark.log.print.appName:#{null}}")
    protected String                 appName;

    /**
     * 动态排除的class（某些class不一定在依赖中），转换那些无法被JSON序列化的对象，比如：ServletRequest/
     * ServletResponse
     */
    private static final List<Class> dynamicExcludeClass      = new ArrayList<>();
    private static final boolean     SERVLET_REQUEST_PRESENT  = ClassUtils.isPresent("javax.servlet.ServletRequest",
                                                                      AbstractMonitorLog.class.getClassLoader());
    private static final boolean     SERVLET_RESPONSE_PRESENT = ClassUtils.isPresent("javax.servlet.ServletResponse",
                                                                      AbstractMonitorLog.class.getClassLoader());

    static {
        if (SERVLET_REQUEST_PRESENT) {
            Class<?> aClass = ClassUtils.resolveClassName("javax.servlet.ServletRequest",
                    AbstractMonitorLog.class.getClassLoader());
            dynamicExcludeClass.add(aClass);
        }
        if (SERVLET_RESPONSE_PRESENT) {
            Class<?> aClass = ClassUtils.resolveClassName("javax.servlet.ServletResponse",
                    AbstractMonitorLog.class.getClassLoader());
            dynamicExcludeClass.add(aClass);
        }
    }

    /**
     * 转换那些无法被JSON序列化的对象，比如：ServletRequest/ServletResponse
     *
     * @param arguments 待转换的方法参数数组对象
     * @return 转换后的方法参数数组对象
     */
    protected Object[] convertArgs(Object[] arguments) {
        for (int i = 0; i < arguments.length; i++) {
            Object value = arguments[i];
            if (value == null) {
                continue;
            }
            convertArgs(arguments, i, value);
        }
        return arguments;
    }

    protected void convertArgs(Object[] arguments, int i, Object value) {
        if (value instanceof Writer) {
            arguments[i] = value.getClass().getName();
        } else {
            for (Class exclude : dynamicExcludeClass) {
                if (exclude.isInstance(value)) {
                    arguments[i] = value.getClass().getName();
                    break;
                }
            }
        }
    }

    protected String toJSONString(Object input) {
        return JSONObject.toJSONStringWithDateFormat(input, "yyyy-MM-dd HH:mm:ss.SSS");
    }
}
