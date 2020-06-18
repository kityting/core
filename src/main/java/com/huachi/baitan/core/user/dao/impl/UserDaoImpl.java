/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.user.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.huachi.baitan.core.componet.mybatis.mapper.UserMapper;
import com.huachi.baitan.core.user.dao.UserDao;
import com.huachi.baitan.core.user.entity.User;

/**
 * 类UserDaoImpl的实现描述：用户数据库接口实现类
 *
 * @author weiliting 2020年6月17日 PM2:45:26
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }
}
