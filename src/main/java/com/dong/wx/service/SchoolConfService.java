package com.dong.wx.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 校园配置service
 *
 * @Author dongzhihua
 * @Date 2020-02-07 15:13
 */
@Component
public class SchoolConfService {

    @Resource
    RedisTemplate<String, String> redisTemplate;

    /**
     * 通过回调toUser参数获取accessToken
     * 先获取appId，再获取accessToken
     * @param toUser 公共号设置里面的原始ID，见： https://mp.weixin.qq.com/cgi-bin/settingpage?t=setting/index&action=index&token=1828162669&lang=zh_CN
     * @return
     */
    public String getAccessTokenByUser(String toUser) {
        return redisTemplate.opsForValue().get("accessToken");
    }

    public String getAccessTokenByAppId(String appId) {
        return null; // TODO
    }
}
