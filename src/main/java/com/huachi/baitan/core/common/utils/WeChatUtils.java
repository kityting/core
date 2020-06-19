/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.common.utils;

import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.SortedMap;
import java.util.Map.Entry;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.alibaba.fastjson.JSON;
import com.huachi.baitan.core.common.constants.SharkConstants;
import com.huachi.baitan.core.common.log.BizRuntimeException;
import com.huachi.baitan.core.common.wechat.dto.WeChatUserInfo;

/**
 * 类WeChatUtils的实现描述：微信工具类
 *
 * @author weiliting 2020年6月19日 AM11:17:21
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WeChatUtils {
    static {
        // 初始化 {@link #decryptedInfo}
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 使用MD5签名请求参数
     *
     * <pre>
     * 数据包如下：
     * <code>
     *   <xml>
     *      <appid>wxd930ea5d5a258f4f</appid>
     *      <mch_id>10000100</mch_id>
     *      <device_info>1000</device_info>
     *      <body>test</body>
     *      <nonce_str>ibuaiVcKdpRxkhJA</nonce_str>
     *      <sign>9A0A8659F005D6984697E2CA0A9CF3B7</sign>
     *   </xml>
     * </code>
     * </pre>
     *
     * 签名算法步骤：
     * <ul>
     * <li>第一步：对参数按照key=value的格式，并按照参数名ASCII字典序排序如下：
     *
     * <pre>
     * <code>
     *   stringA="appid=wxd930ea5d5a258f4f&body=test&device_info=1000&mch_id=10000100&nonce_str=ibuaiVcKdpRxkhJA";
     * </code>
     * </pre>
     *
     * </li>
     * <li>第二步：拼接API密钥：
     *
     * <pre>
     * <code>
     *   stringSignTemp="stringA&key=192006250b4c09247ec02edce69f6a2d"
     *   sign=MD5(stringSignTemp).toUpperCase()="9A0A8659F005D6984697E2CA0A9CF3B7"
     * </code>
     * </pre>
     *
     * </li>
     * <li>第三步：验证调用返回或微信主动通知签名时，传送的sign参数不参与签名，将生成的签名与该sign值作校验</li>
     * </ul>
     *
     * @param paramMap 所有发送或者接收到的数据为集合M
     * @param key key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
     * @return 签名字符
     * @see #checkMd5Sign(SortedMap, String)
     */
    public static String md5SignParams(SortedMap<String, String> paramMap, String key) {
        StringBuilder sb = new StringBuilder();
        for (Entry<String, String> next : paramMap.entrySet()) {
            String k = next.getKey();
            String v = next.getValue();
            if (StringUtils.isBlank(v)) {
                // 如果参数的值为空不参与签名
                continue;
            } else if ("sign".equals(k)) {
                // 传送的sign参数不参与签名
                continue;
            }
            sb.append(k).append("=").append(v).append("&");
        }
        sb.append("key=").append(key);
        return DigestUtils.md5Hex(sb.toString()).toUpperCase();
    }

    /**
     * 使用MD5签名请求参数，详情参考 {@link #md5SignParams(SortedMap, String)}
     *
     * @param paramMap 所有发送或者接收到的数据为集合M
     * @param key key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
     * @return true: 签名相同, true: 签名不相同
     * @see #md5SignParams(SortedMap, String)
     */
    public static String addMd5Sign(SortedMap<String, String> paramMap, String key) {
        String sign = md5SignParams(paramMap, key);
        paramMap.put("sign", sign);
        return sign;
    }

    /**
     * 使用MD5签名请求参数，详情参考 {@link #md5SignParams(SortedMap, String)}
     *
     * @param paramMap 所有发送或者接收到的数据为集合M
     * @param key key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
     * @return true: 签名相同, true: 签名不相同
     * @see #md5SignParams(SortedMap, String)
     */
    public static boolean checkMd5Sign(SortedMap<String, String> paramMap, String key) {
        // 传送的sign参数，将生成的签名与该sign值作校验
        String signInput = paramMap.get("sign");
        String sign = md5SignParams(paramMap, key);
        return sign.equals(signInput);
    }

    /**
     * 解密敏感数据
     *
     * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
     * @param sessionKey 数据进行加密签名的密钥
     * @param iv 对称解密算法初始向量
     * @return 解析成功后的信息
     */
    public static String decryptedInfo(String encryptedData, String sessionKey, String iv) {
        if (StringUtils.isBlank(encryptedData) || StringUtils.isBlank(sessionKey) || StringUtils.isBlank(iv)) {
            return null;
        }
        try {
            // 被加密的数据
            byte[] dataByte = Base64.decodeBase64(encryptedData);
            // 加密秘钥
            byte[] keyByte = Base64.decodeBase64(sessionKey);
            // 偏移量
            byte[] ivByte = Base64.decodeBase64(iv);

            // 如果密钥不足16位，那么就补足.
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }

            // 初始化
            //            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                return new String(resultByte, SharkConstants.DEFAULT_CHARSET);
            }
        } catch (Exception e) {
            throw new BizRuntimeException("decryptedUserInfo failed, encryptedData: " + encryptedData
                    + ", sessionKey: " + sessionKey + ", iv: " + iv, e);
        }
        return null;
    }

    /**
     * 解密用户敏感数据获取用户信息
     *
     * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
     * @param sessionKey 数据进行加密签名的密钥
     * @param iv 对称解密算法初始向量
     * @return 解析成功后的用户信息
     */
    public static WeChatUserInfo decryptedUserInfo(String encryptedData, String sessionKey, String iv) {
        String info = decryptedInfo(encryptedData, sessionKey, iv);
        return JSON.parseObject(info, WeChatUserInfo.class);
    }

}
