/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.common.constants;

import static com.huachi.baitan.core.common.constants.SharkConstants.DEPLOY_ENV;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.apache.commons.lang3.StringUtils;

/**
 * 类DeployEnvEnum的实现描述：部署的环境变量
 *
 * @see SharkConstants#DEPLOY_ENV
 */
@AllArgsConstructor
public enum DeployEnvEnum {
    /**
     * 开发环境
     */
    DEV("dev", "开发环境"),
    /**
     * 开发环境
     */
    TEST("test", "测试环境"),
    /**
     * 预发环境
     */
    PRE("pre", "预发环境"),
    /**
     * 生产环境
     */
    PRD("prd", "生产环境");

    @Getter
    private String        value;
    @Getter
    private String        name;
    private static String deployEnv;

    public static DeployEnvEnum getEnumByValue(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        for (DeployEnvEnum item : DeployEnvEnum.values()) {
            if (Objects.equals(item.getValue(), value)) {
                return item;
            }
        }
        return null;
    }

    /**
     * 是否为开发环境或者测试环境
     *
     * @param value 部署的环境变量
     * @return true: 是; false: 不是
     */
    public static boolean isDevOrTest(String value) {
        return DEV.getValue().equals(value) || TEST.getValue().equals(value);
    }

    /**
     * 是否为开发环境或者测试环境
     *
     * @return true: 是; false: 不是
     */
    public static boolean isDevOrTest() {
        String value = getDeployEnv();
        return DEV.getValue().equals(value) || TEST.getValue().equals(value);
    }

    /**
     * 是否为开发环境
     *
     * @param value 部署的环境变量
     * @return true: 是; false: 不是
     */
    public static boolean isDev(String value) {
        return DEV.getValue().equals(value);
    }

    /**
     * 是否为开发环境
     *
     * @return true: 是; false: 不是
     */
    public static boolean isDev() {
        String value = getDeployEnv();
        return DEV.getValue().equals(value);
    }

    /**
     * 是否为测试环境
     *
     * @param value 部署的环境变量
     * @return true: 是; false: 不是
     */
    public static boolean isTest(String value) {
        return TEST.getValue().equals(value);
    }

    /**
     * 是否为测试环境
     *
     * @return true: 是; false: 不是
     */
    public static boolean isTest() {
        String value = getDeployEnv();
        return TEST.getValue().equals(value);
    }

    /**
     * 是否为预发环境
     *
     * @param value 部署的环境变量
     * @return true: 是; false: 不是
     */
    public static boolean isPre(String value) {
        return PRE.getValue().equals(value);
    }

    /**
     * 是否为预发环境
     *
     * @return true: 是; false: 不是
     */
    public static boolean isPre() {
        String value = getDeployEnv();
        return PRE.getValue().equals(value);
    }

    /**
     * 是否为生产环境
     *
     * @param value 部署的环境变量
     * @return true: 是; false: 不是
     */
    public static boolean isPrd(String value) {
        return PRD.getValue().equals(value);
    }

    /**
     * 是否为生产环境
     *
     * @return true: 是; false: 不是
     */
    public static boolean isPrd() {
        String value = getDeployEnv();
        return PRD.getValue().equals(value);
    }

    /**
     * 获取应用部署的环境变量
     *
     * @return 部署的环境变量
     */
    public static String getDeployEnv() {
        if (deployEnv == null) {
            deployEnv = StringUtils.defaultIfBlank(System.getProperty(DEPLOY_ENV), System.getenv(DEPLOY_ENV));
        }
        return deployEnv;
    }

    /**
     * 获取应用部署的环境变量
     *
     * @return 部署的环境变量
     * @see #getDeployEnv()
     */
    public static DeployEnvEnum getDeployEnvEnum() {
        String deployEnv = getDeployEnv();
        return getEnumByValue(deployEnv);
    }
}
