package com.dong.wx.beans;

import lombok.Data;

/**
 * 库存报警事件推送bean
 *
 * @Author dongzhihua
 * @Date 2020-02-06 10:45
 */
@Data
public class WxCardSkuWarnBean {
    private String ToUserName;
    private String FromUserName;
    private String CreateTime;
    private String MsgType;
    private String Event;
    private String CardId;
    private String Detail;
}
