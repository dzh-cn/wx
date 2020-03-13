package com.jd.jr.wx.ctl;

import com.jd.jr.wx.beans.mini.MiniCode2SessionRes;
import com.jd.jr.wx.constants.RedisKey;
import com.jd.jr.wx.event.MiniUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 小程序控制器
 *
 * @Author dongzhihua
 * @Date 2020-02-20 22:45
 */
@RestController
@RequestMapping("mini")
@Slf4j
public class MiniCtl {

    static final String appId = "wx7f1cfac998f1ee9d";
    static final String secret = "33c471b9abe555740de465044b294f35";
    @Resource
    RedisTemplate<String, String> redisTemplate;

    @RequestMapping("session-key")
    public String getSessionKey(String code) {
        log.info("授权码获取会话，code: {}", code);
        MiniCode2SessionRes res = MiniUtil.code2Sesion(appId, secret, code);
        if (StringUtils.isNotEmpty(res.getSession_key())) {
            redisTemplate.opsForValue().set(RedisKey.mini_session_key, res.getSession_key());
        }
        log.info("session: {}", res);
        return res.getSession_key();
    }

}
