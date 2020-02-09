package com.dong.wx.beans;

import lombok.Data;

/**
 * 微信卡包删除事件通知bean
 *
 * @Author dongzhihua
 * @Date 2020-02-06 10:40
 */
@Data
public class WxCardDelEventBean {
    private String ToUserName;
    private String FromUserName;
    private String CreateTime;
    private String MsgType;
    private String Event;
    private String CardId;
    private String UserCardCode;
}
