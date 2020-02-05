package com.dong.wx.beans;

import java.io.Serializable;

/**
 * 微信领卡事件bean
 *
 * @Author dongzhihua
 * @Date 2020-02-05 22:37
 */
public class WxGetCardActionBean implements Serializable {
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

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getIsGiveByFriend() {
        return isGiveByFriend;
    }

    public void setIsGiveByFriend(String isGiveByFriend) {
        this.isGiveByFriend = isGiveByFriend;
    }

    public String getUserCardCode() {
        return userCardCode;
    }

    public void setUserCardCode(String userCardCode) {
        this.userCardCode = userCardCode;
    }

    public String getFriendUserName() {
        return friendUserName;
    }

    public void setFriendUserName(String friendUserName) {
        this.friendUserName = friendUserName;
    }

    public String getOuterId() {
        return outerId;
    }

    public void setOuterId(String outerId) {
        this.outerId = outerId;
    }

    public String getOldUserCardCode() {
        return oldUserCardCode;
    }

    public void setOldUserCardCode(String oldUserCardCode) {
        this.oldUserCardCode = oldUserCardCode;
    }

    public String getOuterStr() {
        return outerStr;
    }

    public void setOuterStr(String outerStr) {
        this.outerStr = outerStr;
    }

    public String getIsRestoreMemberCard() {
        return isRestoreMemberCard;
    }

    public void setIsRestoreMemberCard(String isRestoreMemberCard) {
        this.isRestoreMemberCard = isRestoreMemberCard;
    }

    public String getIsRecommendByFriend() {
        return isRecommendByFriend;
    }

    public void setIsRecommendByFriend(String isRecommendByFriend) {
        this.isRecommendByFriend = isRecommendByFriend;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }
}
