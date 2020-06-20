/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.user.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.huachi.baitan.core.componet.mybatis.mapper.WxUserMapper;
import com.huachi.baitan.core.user.dao.WxUserDao;
import com.huachi.baitan.core.user.entity.WxUser;

/**
 * 类WxUserDaoImpl的实现描述：微信用户信息实现类
 *
 * @author weiliting 2020年6月19日 PM4:04:52
 */
@Repository
public class WxUserDaoImpl implements WxUserDao {
    @Autowired
    private WxUserMapper wxUserMapper;

    @Override
    public List<WxUser> findWxUserByCondition(WxUser condition) {
        return wxUserMapper.selectByCondition(condition);
    }

    @Override
    public Integer createSelective(WxUser condition) {
        return wxUserMapper.insertSelective(condition);
    }
}
