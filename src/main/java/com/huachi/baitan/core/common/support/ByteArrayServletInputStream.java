/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.common.support;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

/**
 * 类ByteArrayServletInputStream的实现描述：实现{@link ServletInputStream}，使用
 * {@code ByteArrayInputStream} 支持多次读取 {@code byte[]}
 *
 * @see ReaderBodyHttpServletRequestWrapper
 */
public class ByteArrayServletInputStream extends ServletInputStream {
    private final ByteArrayInputStream byteArrayInputStream;

    public ByteArrayServletInputStream(byte[] body) {
        this.byteArrayInputStream = new ByteArrayInputStream(body);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int read() throws IOException {
        return byteArrayInputStream.read();
    }

    /**
     * 是否支持重置 {@link #reset()}
     */
    @Override
    public boolean markSupported() {
        return true;
    }

    /**
     * 把pos的指针的位置重置为起始位置
     */
    @Override
    public synchronized void reset() throws IOException {
        byteArrayInputStream.reset();
    }
}
