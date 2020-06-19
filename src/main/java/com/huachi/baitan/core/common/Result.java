/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.EqualsAndHashCode;

import com.alibaba.fastjson.JSONObject;

/**
 * 类Result的实现描述：方法执行返回结果包装类
 *
 * @author weiliting 2020年6月14日 PM3:56:27
 */

@EqualsAndHashCode
public class Result<T> implements Serializable {
    private static final long   serialVersionUID = -5717660307097449618L;
    private boolean             isSuccess        = true;
    private String              errorMsg;
    private String              errorCode;
    private T                   value;
    private Map<String, Object> extraInfo;

    public Result() {
        super();
    }

    /**
     * 初始化结果值
     *
     * @param value 结果值
     */
    public Result(T value) {
        super();
        this.value = value;
    }

    /**
     * 初始化错误信息，会设置<code>isSuccess = false</code>
     *
     * @param errorMsg 错误信息
     * @param errorCode 错误代码
     */
    public Result(String errorMsg, String errorCode) {
        super();
        this.isSuccess = false;
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }

    /**
     * @param success 是否成功
     * @param errorMsg 错误信息
     * @param errorCode 错误代码
     */
    public Result(boolean success, String errorMsg, String errorCode) {
        super();
        this.isSuccess = success;
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }

    /**
     * @return 是否成功
     */
    public boolean isSuccess() {
        return isSuccess;
    }

    /**
     * @param success 设置是否成功
     */
    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    /**
     * @return 错误信息
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * @param errorMsg 设置错误信息
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * @return 错误代码
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode 设置错误代码
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return 结果值
     */
    public T getValue() {
        return value;
    }

    /**
     * @param value 设置结果值
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * @return 扩展信息
     */
    public Map<String, Object> getExtraInfo() {
        return extraInfo;
    }

    /**
     * @param extraInfo 设置扩展信息
     */
    public void setExtraInfo(Map<String, Object> extraInfo) {
        this.extraInfo = extraInfo;
    }

    /**
     * 增加扩展信息
     *
     * @param key 扩展信息中的key
     * @param value 扩展信息中的value
     */
    public Result<T> addExtraInfo(String key, Object value) {
        if (this.extraInfo == null) {
            this.extraInfo = new HashMap<>();
        }
        this.extraInfo.put(key, value);
        return this;
    }

    /**
     * 获取扩展信息
     *
     * @param key 扩展信息中的key
     * @return 扩展信息中的value
     */
    public Object getExtraInfo(String key) {
        return this.extraInfo == null ? null : this.extraInfo.get(key);
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
