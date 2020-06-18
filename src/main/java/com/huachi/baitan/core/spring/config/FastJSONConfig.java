/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.spring.config;

import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.util.IOUtils;
import com.google.common.collect.Lists;

/**
 * 类FastJSONConfig的实现描述：Fast JSON的全局配置
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FastJSONConfig {
    /**
     * Fast JSON的全局配置
     */
    public static FastJsonConfig fastJsonConfig() {
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect // 关闭循环引用，输出的json字符串中有："$ref"
                , SerializerFeature.WriteMapNullValue // 将 null 字段输出字段名
                );
        fastJsonConfig.setCharset(IOUtils.UTF8);
        return fastJsonConfig;
    }

    /**
     * 使用Fast JSON输出json
     * <p>
     * 如果直接注入，此转换器会添加在“转换器集合”的最前面，在输出/输入String类型的数据的时候，会将<code>"</code>添加转义符
     * <p>
     * 正因为添加了转义符，导致“网关”将String类型的数据发送给“业务系统”、回写给“客户端”，出现了本不应该出现的转义符
     * <p>
     * 解决办法：不直接注入，使用 {@link WebMvcConfigurerAdapter#extendMessageConverters} 添加
     * <p>
     * 使用 {@link WebMvcConfigurerAdapter#configureMessageConverters} 添加，不能解决此问题
     */
    public static FastJsonHttpMessageConverter fastJsonHttpMessageConverter(FastJsonConfig fastJsonConfig) {
        FastJsonHttpMessageConverter jsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        jsonHttpMessageConverter.setDefaultCharset(IOUtils.UTF8);
        List<MediaType> mediaTypes = Lists.newArrayList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON_UTF8);
        jsonHttpMessageConverter.setSupportedMediaTypes(mediaTypes);
        jsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        return jsonHttpMessageConverter;
    }
}
