package com.jd.jr.wx.event;

import com.jd.jr.wx.beans.mini.MiniCode2SessionRes;
import com.jd.jr.wx.utils.HttpUtil;
import com.jd.jr.wx.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 小程序工具类
 *
 * @Author dongzhihua
 * @Date 2020-02-20 22:55
 */
@Slf4j
public abstract class MiniUtil {

    public static final String code2sessionUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=&s&js_code=&s&grant_type=authorization_code";

    public static MiniCode2SessionRes code2Sesion(String appId, String secret, String code) {
        String url = String.format(code2sessionUrl, appId, secret, code);
        try {
            String res = HttpUtil.sendGet(url);
            log.info("授权码获取会话结果：{}", res);
            MiniCode2SessionRes resObj = JsonUtils.readValue(res, MiniCode2SessionRes.class);
            return resObj;
        } catch (Exception e) {
            throw new RuntimeException("授权码获取会话异常");
        }
    }
}
