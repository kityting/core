/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.user.entity;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 类UserDO的实现描述：user表
 *
 * @author weiliting 2020年6月16日 PM5:48:35
 */

@ToString(callSuper = true)
@Data
public class User {
    /**
     * Database Column Name: user.id
     * <p>
     * Database Column Remarks: 用户ID
     */
    private Integer id;

    /**
     * Database Column Name: user.phone
     * <p>
     * Database Column Remarks: 手机号
     */
    private String  phone;

    /**
     * Database Column Name: user.name
     * <p>
     * Database Column Remarks: 用户名
     */
    private String  name;

    /**
     * Database Column Name: user.head_url
     * <p>
     * Database Column Remarks: 头像
     */
    private String  headUrl;
    /**
     * Database Column Name: user.id_deleted
     * <p>
     * Database Column Remarks: 是否删除
     */
    private Integer isDeleted;

    /**
     * Database Column Name: user.create_time
     * <p>
     * Database Column Remarks: 创建时间
     */
    private Date    createTime;

    /**
     * Database Column Name: user.update_time
     * <p>
     * Database Column Remarks: 更新时间
     */
    private Date    updateTime;

}
