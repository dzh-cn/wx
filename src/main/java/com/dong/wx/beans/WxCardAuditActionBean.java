package com.dong.wx.beans;

import lombok.Data;

/**
 * 卡片审核事件推送bean
 *
 * @Author dongzhihua
 * @Date 2020-02-06 09:39
 */
@Data
public class WxCardAuditActionBean {
    private String toUserName;
    private String fromUserName;
    private String createTime;
    private String msgType;
    private String event;
    private String cardId;
    private String refuseReason;

}
