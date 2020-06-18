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

/**
 * 类Result的实现描述：方法执行返回结果包装类
 *
 * @author weiliting 2020年6月14日 PM3:56:27
 */

public class Result<T> implements Serializable {

    private static final long   serialVersionUID = 2057948781441813066L;
    private boolean             isSuccess        = false;
    private String              errorMsg         = "";
    private String              errorCode        = "";
    private T                   value;
    private Map<String, Object> additionalInfo   = new HashMap<String, Object>();

    public Result() {
        super();
    }

    public Result(T value) {
        super();
        this.isSuccess = true;
        this.value = value;
    }

    public Result(String errorMsg, String errorCode) {
        super();
        this.isSuccess = false;
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }

    public Result(boolean success, String errorMsg, String errorCode) {
        super();
        this.isSuccess = success;
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void appendAdditionalInfo(String key, Object value) {
        this.additionalInfo.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <P> P getAdditionalInfo(String key) {
        return (P) additionalInfo.get(key);
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
