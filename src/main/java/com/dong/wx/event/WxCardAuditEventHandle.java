package com.dong.wx.event;

import com.dong.wx.beans.WxCardAuditEventBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 卡审核时间通知
 *
 * @Author dongzhihua
 * @Date 2020-02-07 11:01
 */
@Component("event$card_pass_check")
public class WxCardAuditEventHandle implements EventHandle<WxCardAuditEventBean> {

    Logger log = LoggerFactory.getLogger(WxCardAuditEventHandle.class);

    @Override
    public boolean notice(WxCardAuditEventBean msg) {
        log.info("notice: {}", msg);
        // 上传卡片编号
        return false;
    }
}
