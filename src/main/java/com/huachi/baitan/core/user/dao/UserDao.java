/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.user.dao;

import com.huachi.baitan.core.user.entity.User;

/**
 * 类UserDao的实现描述：用户数据库接口
 *
 * @author weiliting 2020年6月17日 PM2:44:37
 */
public interface UserDao {
    /**
     * 新增
     *
     * @param user 用户信息
     * @return 是否成功
     */
    int insert(User user);
}
