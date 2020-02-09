package com.dong.wx.beans;

import lombok.Data;

/**
 * 发送消息事件bean
 *
 * @Author dongzhihua
 * @Date 2020-02-07 15:49
 */
@Data
public class WxMsgEventBean {
    private String fromUserName;
    private String msgType;
    private String createTime;
    private String toUserName;
    private String msgId;
    private String content;
}
