/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.common.constants;

import org.springframework.util.StringUtils;

/**
 * 类SharkErrorMsg的实现描述：shark项目code代码，用于接口之间交互
 */
public enum SharkCodeMsgEnum {
    /**
     * 成功
     */
    OK("00", "OK"),
    /**
     * 任务未完成
     */
    TASK_NOT_COMPLETE("100", "task is not complete"),
    /**
     * 记录找不到
     */
    RECORD_NOT_EXISTS("01", "record is not exists"),
    /**
     * 没有匹配的记录
     */
    NOT_MATCH_RECORDS("02", "not match records"),
    /**
     * 字段没有值
     */
    VALUE_IS_BLANK("03", "field value is blank"),
    /**
     * 字段的值无效，不符合预期的格式
     */
    VALUE_IS_INVALID("04", "field value is invalid"),
    /**
     * 匹配到了多条的记录
     */
    MORE_RECORDS("05", "more than one record"),
    /**
     * 重复提交
     */
    REPEATED_SUBMIT("06", "repeated submit"),
    /**
     * 黑名单用户
     */
    USER_IS_BLACKLIST("07", "user is blacklist"),
    /**
     * 非法请求
     */
    ILLEGAL_REQUEST("08", "illegal request"),
    /**
     * 用户证件号码已经被绑定了
     */
    USER_ID_NO_IS_BIND("09", "user id no is bind"),
    /**
     * 更新缓存异常
     */
    UPDATE_CACHE_EXCEPTION("10", "update cache exception"),
    /**
     * 系统异常
     */
    SYSTEM_EXCEPTION("11", "system exception"),
    /**
     * 灰名单用户
     */
    USER_IS_GRAYLIST("12", "user is graylist"),

    /**
     * 远程调用异常
     */
    REMOTE_EXCEPTION("13", "remote exception"),

    /**
     * 库存不足
     */
    INSUFFICIENT_INVENTORY("14", "insufficient inventory"),
    /**
     * 灰名单用户
     */
    USER_INFO_EXCEPTION("15", "user infomation exception"),
    /**
     * 存在相同的记录，一般用于幂等校验
     */
    RECORD_ALREADY_EXISTS("16", "record already exists");

    private String code;
    private String msg;

    SharkCodeMsgEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static SharkCodeMsgEnum getEnumByCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        for (SharkCodeMsgEnum item : SharkCodeMsgEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
