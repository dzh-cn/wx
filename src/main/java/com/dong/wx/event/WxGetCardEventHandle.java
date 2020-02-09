package com.dong.wx.event;

import com.dong.wx.beans.WxGetCardEventBean;
import com.dong.wx.service.SchoolConfService;
import com.dong.wx.utils.WeChatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 卡领取事件通知
 *
 * @Author dongzhihua
 * @Date 2020-02-07 11:01
 */
@Component("event$user_get_card")
public class WxGetCardEventHandle implements EventHandle<WxGetCardEventBean> {

    Logger log = LoggerFactory.getLogger(WxGetCardEventHandle.class);

    @Resource
    SchoolConfService schoolConfService;

    @Override
    public boolean notice(WxGetCardEventBean msg) {
        log.info("事件: {}", msg);
        String accessToken = schoolConfService.getAccessTokenByUser(msg.getToUserName());
        String s = WeChatUtil.activeCard(accessToken, "10000001", msg.getUserCardCode(), msg.getCardId(), "http://mmbiz.qpic.cn/mmbiz_jpg/A5vYnILS59VktFAT5c4biaUoLakQ3icict9mLJwsOXHzIkmicESem65TGlSibUSYfsfPRLdKCL4icvJoAOkB7yMTfgBA/0");
        log.info("激活卡片结果 res: {}", s);
        return true;
    }
}
