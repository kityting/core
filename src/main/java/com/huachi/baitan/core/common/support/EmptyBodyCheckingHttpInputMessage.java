/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.common.support;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;

/**
 * 类EmptyBodyCheckingHttpInputMessage的实现描述：检查HTTP input message 的
 * {@linkplain #getBody() body}，如果body中没有值，则返回{@code null}
 */
public class EmptyBodyCheckingHttpInputMessage implements HttpInputMessage {
    private final HttpHeaders headers;
    private final InputStream body;
    private final HttpMethod  method;

    public EmptyBodyCheckingHttpInputMessage(HttpInputMessage inputMessage) throws IOException {
        this.headers = inputMessage.getHeaders();
        InputStream inputStream = inputMessage.getBody();
        if (inputStream == null) {
            this.body = null;
        } else if (inputStream.markSupported()) {
            inputStream.reset(); // 防止已经被读过了
            inputStream.mark(1);
            this.body = inputStream.read() != -1 ? inputStream : null;
            inputStream.reset();
        } else {
            PushbackInputStream pushbackInputStream = new PushbackInputStream(inputStream);
            int b = pushbackInputStream.read();
            if (b == -1) {
                this.body = null;
            } else {
                this.body = pushbackInputStream;
                pushbackInputStream.unread(b);
            }
        }
        this.method = ((HttpRequest) inputMessage).getMethod();
    }

    @Override
    public HttpHeaders getHeaders() {
        return this.headers;
    }

    @Override
    public InputStream getBody() throws IOException {
        return this.body;
    }

    public HttpMethod getMethod() {
        return this.method;
    }
}
