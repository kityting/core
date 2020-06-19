/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.huachi.baitan.core.spring.config.FastJSONConfig;

/**
 * 类FastJsonUtils的实现描述：Fast JSON extend API
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FastJsonUtils {
    /**
     * Fast JSON的全局配置
     */
    public static final FastJsonConfig GLOBAL_FAST_JSON_CONFIG = FastJSONConfig.fastJsonConfig();

    /**
     * 将 JSON 字符串转换为 JSONObject 对象，如果 JSON 字符串为空字符串或者 NULL，则创建一个 JSONObject 对象
     *
     * @param jsonString JSON字符串（可以是空字符串或者 NULL）
     * @return JSONObject对象
     */
    public static JSONObject parseObject(String jsonString) {
        JSONObject result = JSON.parseObject(jsonString);
        return result == null ? new JSONObject() : result;
    }

    /**
     * 将 JSON 字符串转换为 JSONArray 对象，如果 JSON 字符串为空字符串或者 NULL，则创建一个 JSONArray 对象
     *
     * @param jsonString JSON字符串（可以是空字符串或者 NULL）
     * @return JSONArray对象
     */
    public static JSONArray parseArray(String jsonString) {
        JSONArray result = JSON.parseArray(jsonString);
        return result == null ? new JSONArray() : result;
    }

    /**
     * 输出JSON字符串，日期会格式化，其它格式化参考：{@link FastJSONConfig#fastJsonConfig()}
     *
     * @param object 待转换待对象
     * @return JSON字符串
     */
    public static String toJSONString(Object object) {
        return JSON.toJSONString(object, GLOBAL_FAST_JSON_CONFIG.getSerializeConfig(),
                SerializerFeature.WriteDateUseDateFormat);
    }

}
