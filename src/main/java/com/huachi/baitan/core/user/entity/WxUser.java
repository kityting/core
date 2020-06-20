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
 * 类UserDO的实现描述：wx_user表
 *
 * @author weiliting 2020年6月16日 PM5:48:35
 */

@ToString(callSuper = true)
@Data
public class WxUser {
    /**
     * Database Column Name: wx_user.id
     * <p>
     * Database Column Remarks: 主键ID
     */
    private Long    id;

    /**
     * Database Column Name: wx_user.user_id
     * <p>
     * Database Column Remarks: 手机号
     */
    private Long    userId;

    /**
     * Database Column Name: wx_user.openid
     * <p>
     * Database Column Remarks: 微信openid
     */
    private String  openid;

    /**
     * Database Column Name: wx_user.unionid
     * <p>
     * Database Column Remarks: 微信unionid
     */
    private String  unionid;
    /**
     * Database Column Name: wx_user.open_user_id
     * <p>
     * Database Column Remarks: openUserId
     */
    private String  openUserId;
    /**
     * Database Column Name: wx_user.open_user_encrypt_id
     * <p>
     * Database Column Remarks: 加密openUserId
     */
    private String  openUserEncryptId;
    /**
     * Database Column Name: wx_user.channel_type
     * <p>
     * Database Column Remarks: 来源渠道
     */
    private Integer channelType;
    /**
     * Database Column Name: wx_user.extended_info
     * <p>
     * Database Column Remarks: 扩展信息
     */
    private String  extendedInfo;
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
