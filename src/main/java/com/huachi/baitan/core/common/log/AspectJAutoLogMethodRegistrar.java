/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.common.log;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.huachi.baitan.core.common.log.annotation.EnableAutoLogMethod;
import com.huachi.baitan.core.common.log.annotation.MonitorMethodAdvice;

/**
 * 类AspectJAutoLogMethodRegistrar的实现描述：扫描当前package下的
 * {@link org.springframework.stereotype.Component}，并进行 Bean 的自动注册
 */
public class AspectJAutoLogMethodRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {
    private ResourceLoader resourceLoader;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(importingClassMetadata
                .getAnnotationAttributes(EnableAutoLogMethod.class.getName()));
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
        scanner.resetFilters(false);
        scanner.setResourceLoader(this.resourceLoader);

        // 监控 method 的入参和出参
        boolean enableLogMethod = attributes.getBoolean("enableLogMethod");
        if (!enableLogMethod) {
            scanner.addExcludeFilter(new AnnotationTypeFilter(MonitorMethodAdvice.class));
        } else {
            scanner.addIncludeFilter(new AnnotationTypeFilter(MonitorMethodAdvice.class));
        }

        // 监控 controller 异常
        boolean enableLogControllerException = attributes.getBoolean("enableLogControllerException");
        if (!enableLogControllerException) {
            scanner.addExcludeFilter(new AnnotationTypeFilter(ControllerAdvice.class));
        } else {
            scanner.addIncludeFilter(new AnnotationTypeFilter(ControllerAdvice.class));
        }

        // 扫描注册
        String packageName = ClassUtils.getPackageName(AspectJAutoLogMethodRegistrar.class);
        scanner.scan(packageName);
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
