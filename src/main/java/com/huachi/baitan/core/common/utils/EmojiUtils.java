/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.common.utils;

import java.util.regex.Pattern;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 类EmojiUtils的实现描述：Emoji表情工具类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmojiUtils {
    /**
     * 将 emoji 表情字符中的"单\"转换为"双\\"
     *
     * @param emoji emoji字符串
     * @return 转换后的字符串
     */
    public static String addBackslash(String emoji) {
        if (StringUtils.isBlank(emoji)) {
            return emoji;
        }
        // 转成unicode
        String unicode = StringEscapeUtils.escapeJava(emoji);

        // 将 emoji 表情字符中的"单\"转换为"双\\"
        String addBackslashUnicode = Pattern.compile("(\\\\u[ed][0-9a-f]{3})", Pattern.CASE_INSENSITIVE)
                .matcher(unicode).replaceAll("\\\\$0");

        // 将unicode转换为字符串
        return StringEscapeUtils.unescapeJava(addBackslashUnicode);
    }

    /**
     * 将添加了"双\\"字符替换为"单\" ，还原成emoji字符
     *
     * @param emoji emoji字符串
     * @return 还原后的字符串
     */
    public static String removeBackslash(String emoji) {
        if (StringUtils.isBlank(emoji)) {
            return emoji;
        }
        return StringEscapeUtils.unescapeJava(emoji.replace("\\\\", "\\"));
    }
}
