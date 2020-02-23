package com.jd.jr.wx.beans.mini;

import lombok.Data;

/**
 * 授权码获取session返回信息
 *
 * @Author dongzhihua
 * @Date 2020-02-20 23:03
 */
@Data
public class MiniCode2SessionRes {
    private String openid; //用户唯一标识
    private String session_key; //会话密钥
    private String unionid; //用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回，详见 UnionID 机制说明。
    private Integer errcode; // 错误码
    private String errmsg; //错误信息
}
