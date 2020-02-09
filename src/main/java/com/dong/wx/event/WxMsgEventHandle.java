package com.dong.wx.event;

import com.dong.wx.beans.WxMsgEventBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 卡审核时间通知
 *
 * @Author dongzhihua
 * @Date 2020-02-07 11:01
 */
@Component("text$")
public class WxMsgEventHandle implements EventHandle<WxMsgEventBean> {

    Logger log = LoggerFactory.getLogger(WxMsgEventHandle.class);

    @Override
    public boolean notice(WxMsgEventBean msg) {
        log.info("notice: {}", msg);
        return false;
    }
}
