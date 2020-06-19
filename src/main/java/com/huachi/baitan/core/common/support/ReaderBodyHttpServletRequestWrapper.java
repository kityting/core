/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.common.support;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.util.StreamUtils;

import com.huachi.baitan.core.common.constants.SharkConstants;

/**
 * 类ReaderBodyHttpServletRequestWrapper的实现描述：HttpServletRequestWrapper包装类， 将流保存为
 * {@code byte[]}，然后将 {@link HttpServletRequestWrapper#getReader()} 和
 * {@link HttpServletRequestWrapper#getInputStream()} 方法的流的读取指向 {@code byte[]}，
 * 使这两个方法支持多次读取，默认只能读取一次。并且能支持 {@link InputStream#reset()} 操作，把pos的指针的位置重置为起始位置。
 */
public class ReaderBodyHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private final Lock         lock   = new ReentrantLock();
    private boolean            marked = false;
    private ServletInputStream inputStream;

    public ReaderBodyHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        String characterEncoding = this.getCharacterEncoding();
        Charset charset = characterEncoding != null ? Charset.forName(characterEncoding)
                : SharkConstants.DEFAULT_CHARSET;
        InputStreamReader inputStreamReader = new InputStreamReader(getInputStream(), charset);
        return new BufferedReader(inputStreamReader);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (!marked) {
            warpInputStream();
        } else {
            // 防止已经被读过了
            this.inputStream.reset();
        }
        return this.inputStream;
    }

    /**
     * 包装{@link ServletInputStream}
     */
    private void warpInputStream() throws IOException {
        lock.lock();
        try {
            if (!marked) {
                ServletInputStream inputStreamSrc = this.getRequest().getInputStream();
                byte[] body = StreamUtils.copyToByteArray(inputStreamSrc);
                this.inputStream = new ByteArrayServletInputStream(body);
            }
            marked = true;
        } finally {
            lock.unlock();
        }
    }
}
