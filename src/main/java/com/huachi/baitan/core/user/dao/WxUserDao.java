/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.user.dao;

import java.util.List;

import com.huachi.baitan.core.user.entity.WxUser;

/**
 * 类WxUserDao的实现描述：微信用户信息
 *
 * @author weiliting 2020年6月19日 PM4:04:12
 */
public interface WxUserDao {
    /**
     * 根据条件查找用户信息
     *
     * @param condition WxUser
     * @return List
     */
    List<WxUser> findWxUserByCondition(WxUser condition);

    /**
     * 创建微信用户数据
     *
     * @param condition 条件
     * @return int
     */
    Integer createSelective(WxUser condition);

    /**
     * 更新用户信息
     *
     * @param condition 条件
     * @return int
     */
    Integer updateByPrimaryKeySelective(WxUser condition);
}
