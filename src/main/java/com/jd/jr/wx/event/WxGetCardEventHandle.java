package com.jd.jr.wx.event;

import com.jd.jr.wx.beans.WxGetCardEventBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 卡领取事件通知
 *
 * @Author dongzhihua
 * @Date 2020-02-07 11:01
 */
@Component("event$user_get_card")
public class WxGetCardEventHandle implements EventHandle<WxGetCardEventBean> {

    Logger log = LoggerFactory.getLogger(WxGetCardEventHandle.class);

    @Override
    public boolean notice(WxGetCardEventBean msg) {
        log.info("事件: {}", msg);
        return true;
    }
}
