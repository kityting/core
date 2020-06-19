/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.huachi.baitan.core.common.constants.SharkCodeMsgEnum;

/**
 * 类TransformDTO的实现描述：交换DTO的数据，比如：错误信息。工具类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransformDTO {

    /**
     * 复制Result中的错误信息，并设置目标中的success = false
     *
     * @param source 源
     * @param target 目标
     * @return target
     */
    @SuppressWarnings("unchecked")
    public static <T> Result<T> copyErrorInfo(Result source, Result<T> target) {
        target.setSuccess(false);
        target.setErrorCode(source.getErrorCode());
        target.setErrorMsg(source.getErrorMsg());
        return target;
    }

    /**
     * 复制Result中的所有信息
     *
     * @param source 源
     * @param target 目标
     * @param <T> 类型
     * @return target
     */
    public static <T> Result<T> copyAllInfo(Result<T> source, Result<T> target) {
        target.setSuccess(source.isSuccess());
        target.setErrorCode(source.getErrorCode());
        target.setErrorMsg(source.getErrorMsg());
        target.setValue(source.getValue());
        target.setExtraInfo(source.getExtraInfo());
        return target;
    }

    /**
     * 设置Result中的错误信息，并设置目标中的success = false
     *
     * @param target 目标
     * @param errorCode 错误代码
     * @param errorMsg 错误信息
     * @param value 设置value值
     * @param <T> 类型
     * @return target
     */
    public static <T> Result<T> setErrorInfo(Result<T> target, String errorCode, String errorMsg, T value) {
        target.setSuccess(false);
        target.setErrorCode(errorCode);
        target.setErrorMsg(errorMsg);
        target.setValue(value);
        return target;
    }

    /**
     * 设置Result中的错误信息，并设置目标中的success = false
     *
     * @param target 目标
     * @param errorCode 错误代码
     * @param errorMsg 错误信息
     * @param <T> 类型
     * @return target
     */
    public static <T> Result<T> setErrorInfo(Result<T> target, String errorCode, String errorMsg) {
        target.setSuccess(false);
        target.setErrorCode(errorCode);
        target.setErrorMsg(errorMsg);
        return target;
    }

    /**
     * 设置Result中的错误信息，并设置目标中的success = false
     *
     * @param target 目标
     * @param errorMsgEnum 错误信息枚举值
     * @param value 设置value值
     * @param <T> 类型
     * @return target
     */
    public static <T> Result<T> setErrorInfo(Result<T> target, SharkCodeMsgEnum errorMsgEnum, T value) {
        target.setSuccess(false);
        target.setErrorCode(errorMsgEnum.getCode());
        target.setErrorMsg(errorMsgEnum.getMsg());
        target.setValue(value);
        return target;
    }

    /**
     * 设置Result中的错误信息，并设置目标中的success = false
     *
     * @param target 目标
     * @param errorMsgEnum 错误信息枚举值
     * @param <T> 类型
     * @return target
     */
    public static <T> Result<T> setErrorInfo(Result<T> target, SharkCodeMsgEnum errorMsgEnum) {
        target.setSuccess(false);
        target.setErrorCode(errorMsgEnum.getCode());
        target.setErrorMsg(errorMsgEnum.getMsg());
        return target;
    }
}
