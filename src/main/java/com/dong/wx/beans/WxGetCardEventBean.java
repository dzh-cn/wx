package com.dong.wx.beans;

import lombok.Data;

import java.io.Serializable;

/**
 * 微信领卡事件bean
 *
 * @Author dongzhihua
 * @Date 2020-02-05 22:37
 */
@Data
public class WxGetCardEventBean implements Serializable {
    private String toUserName;
    private String fromUserName;
    private String createTime;
    private String msgType;
    private String event;
    private String cardId;
    private String isGiveByFriend;
    private String userCardCode;
    private String friendUserName;
    private String outerId;
    private String oldUserCardCode;
    private String outerStr;
    private String isRestoreMemberCard;
    private String isRecommendByFriend;
    private String unionId;

}
