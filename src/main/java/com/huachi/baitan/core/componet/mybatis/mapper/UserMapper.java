/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.componet.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.huachi.baitan.core.user.entity.User;

/**
 * 类UserMapper的实现描述：This mapper interface access the database table user
 *
 * @author weiliting 2020年6月16日 PM5:44:20
 */
@Mapper
public interface UserMapper {

    int insert(User record);

    int insertBatch(List<User> records);

    int insertSelective(User record);

    int deleteByPrimaryKey(Long id);

    int deleteByCondition(User condition);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int updateByCondition(@Param("record") User record, @Param("condition") User condition);

    User selectByPrimaryKey(Long id);

    List<User> selectByCondition(User condition);

    int selectByConditionWithCount(@Param("condition") User condition,
                                   @Param("extraCondition") Map<String, Object> extraCondition);

}
