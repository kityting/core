/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.common.log;

/**
 * 类BizRuntimeException的实现描述：业务运行时异常，主要是为了规避PMD扫描 {@link RuntimeException}
 */
public class BizRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 5543342177361843865L;

    public BizRuntimeException() {
        super();
    }

    public BizRuntimeException(String message) {
        super(message);
    }

    public BizRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizRuntimeException(Throwable cause) {
        super(cause);
    }
}
