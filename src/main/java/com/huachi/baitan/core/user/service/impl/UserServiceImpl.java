/*
 * Copyright 2020 Huachi.com All right reserved. This software is the
 * confidential and proprietary information of Huachi.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Huachi.com.
 */

package com.huachi.baitan.core.user.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.collections.CollectionUtils;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.huachi.baitan.core.common.Result;
import com.huachi.baitan.core.common.TransformDTO;
import com.huachi.baitan.core.common.constants.ChannelTypeEnum;
import com.huachi.baitan.core.common.constants.SharkCodeMsgEnum;
import com.huachi.baitan.core.common.utils.EmojiUtils;
import com.huachi.baitan.core.common.utils.RandomUtils;
import com.huachi.baitan.core.common.utils.RedisUtils;
import com.huachi.baitan.core.common.utils.RestUtils;
import com.huachi.baitan.core.common.utils.WeChatUtils;
import com.huachi.baitan.core.common.wechat.config.WeChatAppProperties;
import com.huachi.baitan.core.common.wechat.config.WeChatAppProperties.WeChatApp;
import com.huachi.baitan.core.common.wechat.dto.WeChatLoginSession;
import com.huachi.baitan.core.common.wechat.dto.WeChatUserInfo;
import com.huachi.baitan.core.user.dao.UserDao;
import com.huachi.baitan.core.user.dao.WxUserDao;
import com.huachi.baitan.core.user.entity.User;
import com.huachi.baitan.core.user.entity.WxUser;
import com.huachi.baitan.core.user.service.UserService;

/**
 * 类UserServiceImpl的实现描述：用户服务实现类
 *
 * @author weiliting 2020年6月17日 PM2:22:17
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private static final String LOGIN_RESPONSE_KEY = "loginResponse";
    @Autowired
    private UserDao             userDao;
    @Autowired
    private WxUserDao           wxUserDao;
    @Resource
    private RedisUtils          redisUtils;
    @Autowired
    private WeChatAppProperties weChatAppProperties;
    @Autowired
    private RestTemplate        restTemplate4OkHttp3;
    @Value("${shark.wechat.service.code2SessionService}")
    private String              wxCode2SessionService;
    @Value("${shark.wechat.app.configs.baitan.appId}")
    private String              appId;

    @Override
    public Result<Boolean> register(User user) {
        Result<Boolean> result = new Result<>(false);
        if (StringUtils.isBlank(user.getPhone())) {
            TransformDTO.setErrorInfo(result, SharkCodeMsgEnum.VALUE_IS_BLANK.getCode(), "phone must not be blank");
        }
        Integer insertResult = userDao.insert(user);
        if (insertResult > 0) {
            result.setSuccess(true);
            result.setValue(true);
        }
        return result;
    }

    @Override
    public Result<String> login(JSONObject params) {
        Result<String> result = new Result<>();
        // 登录时获取的 code
        String loginCode = params.getString("loginCode");
        // 小程序渠道类型
        Integer channelType = params.getInteger("channelType");
        if (StringUtils.isBlank(loginCode) || channelType == null) {
            return TransformDTO.setErrorInfo(result, SharkCodeMsgEnum.VALUE_IS_BLANK.getCode(),
                    "channelType, loginCode must not be null");
        }
        WeChatApp weChatApp = ChannelTypeEnum.getWeChatApp(weChatAppProperties, channelType);
        if (weChatApp == null) {
            return TransformDTO.setErrorInfo(result, SharkCodeMsgEnum.VALUE_IS_INVALID.getCode(),
                    "activityChannel is invalid");
        }

        // 调用微信 code 换取 session_key 接口
        String responseResult = RestUtils.getForString(restTemplate4OkHttp3, wxCode2SessionService,
                weChatApp.getAppId(), weChatApp.getSecret(), loginCode);
        WeChatLoginSession loginSession = JSONObject.parseObject(responseResult, WeChatLoginSession.class);
        if (StringUtils.isNotBlank(loginSession.getErrorCode())) {
            return TransformDTO.setErrorInfo(result, loginSession.getErrorCode(), loginSession.getErrorMsg());
        } else if (StringUtils.isBlank(loginSession.getSessionKey()) || StringUtils.isBlank(loginSession.getOpenid())) {
            return TransformDTO.setErrorInfo(result, SharkCodeMsgEnum.ILLEGAL_REQUEST);
        }
        loginSession.setChannelType(channelType);

        // 如果用户之前已经关注过公众号，微信服务器会返回 union_id，否则就去解密用户的union_id
        if (StringUtils.isBlank(loginSession.getUnionId())) {
            try {
                WeChatUserInfo weChatUserInfo = WeChatUtils.decryptedUserInfo(params.getString("encryptedData"),
                        loginSession.getSessionKey(), params.getString("iv"));
                if (weChatUserInfo != null) {
                    loginSession.setUnionId(weChatUserInfo.getUnionId());
                }
            } catch (Exception ex) {
                log.warn("decryptedUserInfo failed", ex);
            }
        }
        if (StringUtils.isBlank(loginSession.getUnionId())) {
            // 如果解密失败，尝试去表里获取
            WxUser condition = new WxUser();
            condition.setChannelType(channelType);
            condition.setOpenid(loginSession.getOpenid());
            List<WxUser> wxUserList = wxUserDao.findWxUserByCondition(condition);
            if (!wxUserList.isEmpty() && StringUtils.isNotBlank(wxUserList.get(0).getUnionid())) {
                loginSession.setUnionId(wxUserList.get(0).getUnionid());
            }
        }
        // 是否有union_id
        result.addExtraInfo("containsUnionId", StringUtils.isNotBlank(loginSession.getUnionId()));

        // 防止重复调用此接口，导致注册多个渠道用户出来(需要加锁处理，后续更新)
        String openUserIdEncrypt = createWxUser(channelType, loginSession.getOpenid(), loginSession.getUnionId(),
                appId, params);
        result.addExtraInfo("openUserId", openUserIdEncrypt);
        loginSession.setOpenUserIdEncrypt(openUserIdEncrypt);

        // 保存session信息到缓存中间件中
        String sessionUuid = RandomUtils.uuid();
        // 使用微信的缓存时间（如果有）
        Integer expiresIn = loginSession.getExpiresIn() == null ? 3600 : loginSession.getExpiresIn();
        boolean cached = redisUtils.set(sessionUuid, loginSession.toString(), expiresIn);
        if (!cached) {
            log.error("setCache failed: {}", sessionUuid);
            return TransformDTO.setErrorInfo(result, SharkCodeMsgEnum.UPDATE_CACHE_EXCEPTION);
        }
        result.setValue(sessionUuid);
        return result;
    }

    /**
     * 创建微信用户
     *
     * @param channelType 渠道
     * @param openid 微信openid
     * @param unionid 微信unionid
     * @param appId 小程序appID
     * @return 加密openUserId
     */
    public String createWxUser(Integer channelType, String openid, String unionid, String appId, JSONObject params) {
        WxUser condition = new WxUser();
        condition.setChannelType(channelType);
        condition.setOpenid(openid);
        // 用户信息
        JSONObject userInfo = params.getJSONObject("userInfo");
        List<WxUser> openUserList = wxUserDao.findWxUserByCondition(condition);
        if (CollectionUtils.isNotEmpty(openUserList)) {
            // 可异步操作
            if (openUserList.get(0).getUserId() != null && userInfo != null) {
                String nickName = userInfo.getString("nickName");
                String name = EmojiUtils.addBackslash(nickName);
                String headUrl = userInfo.getString("avatarUrl");
                Integer genter = userInfo.getInteger("genter");
                User userCondition = new User();
                userCondition.setId(openUserList.get(0).getUserId());
                List<User> userList = userDao.findUserByCondition(userCondition);
                if (CollectionUtils.isNotEmpty(userList)) {
                    userCondition.setName(name);
                    userCondition.setHeadUrl(headUrl);
                    userCondition.setUpdateTime(new Date());
                    userDao.updateByPrimaryKeySelective(userCondition);
                }
            }
            return openUserList.get(0).getOpenUserEncryptId();
        }
        if (StringUtils.isNotBlank(unionid)) {
            condition.setUnionid(unionid);
        }
        if (userInfo != null) {
            condition.setExtendedInfo(userInfo.toJSONString());
        }
        Date now = new Date();
        condition.setCreateTime(now);
        condition.setUpdateTime(now);
        // 生成用户标示id
        String hmacSha1Hex = HmacUtils.hmacSha1Hex(appId, channelType + openid);
        String openUserId = DigestUtils.md5Hex(hmacSha1Hex.toUpperCase());
        condition.setOpenUserId(openUserId.toUpperCase());
        String openUserIdEncrypt = DigestUtils.md5Hex(HmacUtils.hmacSha1Hex(appId, openUserId));
        condition.setOpenUserEncryptId(openUserIdEncrypt.toUpperCase());
        wxUserDao.createSelective(condition);
        return condition.getOpenUserEncryptId();
    }
}
