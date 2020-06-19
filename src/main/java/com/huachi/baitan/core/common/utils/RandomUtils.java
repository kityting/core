/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.common.utils;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.apache.commons.lang3.StringUtils;

/**
 * 类RandomUtils的实现描述：随机字符串工具类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomUtils {
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 补足n位,如果不够就左边补0, 超过n位的截取前n位
     *
     * @param nextValue 下一位序列号值
     * @param length 序列号最大的长度
     */
    public static String buildSequenceNo(long nextValue, int length) {
        String value = nextValue + "";
        if (value.length() <= length) {
            return StringUtils.leftPad(value, length, '0');
        } else {
            return StringUtils.substring(value, 0, length);
        }
    }
}
