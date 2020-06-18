/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.spring.config;

import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.huachi.baitan.core.common.filter.ReaderBodyHttpServletRequestFilter;
import com.huachi.baitan.core.common.filter.WebRequestLogFilter;

/**
 * 类WebMvcConfig的实现描述：配置Spring WebMvc适配器
 */
public class WebAppMvcConfig extends WebMvcConfigurerAdapter {
    /**
     * @see WebMvcConfigurationSupport#requestMappingHandlerAdapter
     * @see WebMvcConfigurationSupport#getMessageConverters
     * @see FastJSONConfig#fastJsonHttpMessageConverter(FastJsonConfig)
     * @see FastJSONConfig#fastJsonConfig()
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(3, FastJSONConfig.fastJsonHttpMessageConverter(FastJSONConfig.fastJsonConfig()));
        super.extendMessageConverters(converters);
    }

    /**
     * 注册 {@link ReaderBodyHttpServletRequestFilter} Filter
     */
    @Bean
    public FilterRegistrationBean readerBodyHttpServletRequestFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new ReaderBodyHttpServletRequestFilter());
        return registration;
    }

    /**
     * 注册 {@link WebRequestLogFilter} Filter
     */
    @Bean
    public FilterRegistrationBean webRequestLogFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new WebRequestLogFilter(true, true));
        registration.setOrder(100);
        return registration;
    }
}
