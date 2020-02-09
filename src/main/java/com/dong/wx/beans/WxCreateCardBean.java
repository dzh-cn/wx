package com.dong.wx.beans;

import lombok.Data;

/**
 * 微信卡片创建参数bean
 *
 * @Author dongzhihua
 * @Date 2020-02-06 21:10
 */
@Data
public class WxCreateCardBean {
    private String brandName; // 品牌名称
    private String title; // 标题
    private String logoUrl; // logo图片链接
    private String backGroundPicUrl; // 卡片背景链接
    private String activateUrl; // 激活链接 该链接在领卡页面展示点击激活
    private String customField1Name; // tag
    private String customField1Url;
    private String customField2Name;
    private String customField2Url;
    private String customField3Name;
    private String customField3Url;
    private String custom1Url; // 导航
    private String custom1Name;
    private String custom2Url;
    private String custom2Name;
    private String custom3Url;
    private String custom3Name;
    private String prerogative; // 特权说明 卡片详情里展示
    private String description; // 使用说明 卡片详情里展示
}
