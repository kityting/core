/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.apache.commons.lang3.StringUtils;

import com.huachi.baitan.core.common.log.BizRuntimeException;

/**
 * 类IPUtils的实现描述：IP地址工具类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IPUtils {
    /**
     * 获取本机IP
     */
    public static String getLocalIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new BizRuntimeException("Thrown to indicate that the IP address of a host could not be determined", e);
        }
    }

    /**
     * 掩码的IP地址转换为明码
     *
     * @param ip 2886796291
     * @return String xxx.xxx.xxx.xxx
     */
    public static String decodeIp(long ip) {
        if (ip <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append((ip >> 24) & 0xFF).append(".").append((ip >> 16) & 0xFF).append(".").append((ip >> 8) & 0xFF)
                .append(".").append(ip & 0xFF);
        return sb.toString();
    }

    /**
     * 将明码的IP地址转码为数字
     *
     * @param ipString xxx.xxx.xxx.xxx
     * @return long 2886796291
     */
    public static long encodeIp(String ipString) {
        long ipNumber = 0;
        if (StringUtils.isNotBlank(ipString)) {
            String[] ipArray = StringUtils.split(ipString, ".");
            if (ipArray.length == 4) {
                ipNumber = (Long.parseLong(ipArray[0]) << 24) | (Long.parseLong(ipArray[1]) << 16)
                        | (Long.parseLong(ipArray[2]) << 8) | (Long.parseLong(ipArray[3]));
            }
        }
        return ipNumber;
    }

    /**
     * 获取请求客户端的真实地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                ipAddress = getLocalIp();
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP，多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { //**.***.***.***".length() = 15  
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }
}
