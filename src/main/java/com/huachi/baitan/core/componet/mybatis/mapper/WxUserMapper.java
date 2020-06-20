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

import com.huachi.baitan.core.user.entity.WxUser;

/**
 * 类UserMapper的实现描述：This mapper interface access the database table user
 *
 * @author weiliting 2020年6月16日 PM5:44:20
 */
@Mapper
public interface WxUserMapper {

    int insert(WxUser record);

    int insertBatch(List<WxUser> records);

    int insertSelective(WxUser record);

    int deleteByPrimaryKey(Long id);

    int deleteByCondition(WxUser condition);

    int updateByPrimaryKeySelective(WxUser record);

    int updateByPrimaryKey(WxUser record);

    int updateByCondition(@Param("record") WxUser record, @Param("condition") WxUser condition);

    WxUser selectByPrimaryKey(Long id);

    List<WxUser> selectByCondition(WxUser condition);

    int selectByConditionWithCount(@Param("condition") WxUser condition,
                                   @Param("extraCondition") Map<String, Object> extraCondition);

}
