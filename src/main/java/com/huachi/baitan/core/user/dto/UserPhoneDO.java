/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.user.dto;

import lombok.Data;

/**
 * 类UserPhoneDO的实现描述：用户注册数据
 *
 * @author weiliting 2020年6月20日 PM2:05:28
 */
@Data
public class UserPhoneDO {
    /**
     * 所属渠道类型
     *
     * @see com.huachi.baitan.core.common.constants.ChannelTypeEnum 用户渠道类型
     */
    private Integer          channelType;
    /**
     * 联系电话
     */
    private String           phone;
    /**
     * 手机号码校验方法
     */
    private CheckPhoneMethod checkPhoneMethod;
    /**
     * 手机验证码
     */
    private String           smsVerificationCode;
    /**
     * 手机验证码短信模板
     */
    private String           smsTemplateNo;
    /**
     * 对称解密算法初始向量
     */
    private String           iv;
    /**
     * 是否需要为手机号码创建众安账户
     */
    private boolean          needRegisterZaAccount;
    /**
     * 是否需要返回手机号码，用于获取微信授权的手机号码
     */
    private boolean          needReturnPhone;

    public enum CheckPhoneMethod {
        /**
         * 微信授权解密
         */
        WECHAT,
        /**
         * 手机短信校验
         */
        SMS
    }
}
