/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.common.constants;

import java.util.ArrayList;
import java.util.List;

import com.huachi.baitan.core.common.wechat.config.WeChatAppProperties;
import com.huachi.baitan.core.common.wechat.config.WeChatAppProperties.WeChatApp;

/**
 * 类ActivityChannelEnum的实现描述：用户活动渠道类型，
 */
public enum ChannelTypeEnum {
    /**
     * 摆摊小程序
     */
    WE_CHAT_BT(200, "摆摊小程序", "weChatBT", false, ""),

    ;

    private Integer           value;
    private String            name;
    private String            appCode;
    private Boolean           isWeChatApp;
    private String            appCategory;
    private ChannelTypeEnum[] childChannel;

    ChannelTypeEnum(Integer value, String name, String appCode, Boolean isWeChatApp, String appCategory) {
        this.value = value;
        this.name = name;
        this.appCode = appCode;
        this.isWeChatApp = isWeChatApp;
        this.appCategory = appCategory;
    }

    ChannelTypeEnum(Integer value, String name, String appCode, Boolean isWeChatApp, String appCategory,
                    ChannelTypeEnum... childEnum) {
        this(value, name, appCode, isWeChatApp, appCategory);
        this.childChannel = childEnum;
    }

    /**
     * @return App ID值
     */
    public Integer getValue() {
        return value;
    }

    /**
     * @return App名称
     */
    public String getName() {
        return name;
    }

    /**
     * @return App代码
     */
    public String getAppCode() {
        return appCode;
    }

    /**
     * @return 是否为微信小程序
     */
    public Boolean isWeChatApp() {
        return isWeChatApp;
    }

    /**
     * @return 子类小程序
     */
    public ChannelTypeEnum[] getChildActivityChannel() {
        return childChannel;
    }

    /**
     * @return App类别
     */
    public String getAppCategory() {
        return appCategory;
    }

    public static List<ChannelTypeEnum> getEnumByAppCategory(String appCategory) {
        List<ChannelTypeEnum> list = new ArrayList<>();
        if (appCategory == null) {
            return null;
        }
        for (ChannelTypeEnum item : ChannelTypeEnum.values()) {
            if (appCategory.equals(item.getAppCategory())) {
                list.add(item);
            }
        }
        return list;
    }

    public static ChannelTypeEnum getEnumByAppCode(String appCode) {
        if (appCode == null) {
            return null;
        }
        for (ChannelTypeEnum item : ChannelTypeEnum.values()) {
            if (item.getAppCode().equals(appCode)) {
                return item;
            }
        }
        return null;
    }

    public static ChannelTypeEnum getEnumByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (ChannelTypeEnum item : ChannelTypeEnum.values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }

    /**
     * 根据渠道类型，获取微信小程序配置
     *
     * @param weChatAppProperties 微信小程序全部的配置
     * @param activityChannel 渠道类型
     * @return 微信小程序配置
     */
    public static WeChatApp getWeChatApp(WeChatAppProperties weChatAppProperties, Integer activityChannel) {
        ChannelTypeEnum activityChannelEnum = getEnumByValue(activityChannel);
        if (activityChannelEnum == null) {
            return null;
        }
        return weChatAppProperties.getConfigs().get(activityChannelEnum.getAppCode());
    }
}
