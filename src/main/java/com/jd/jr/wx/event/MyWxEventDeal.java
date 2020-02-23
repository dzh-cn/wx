package com.jd.jr.wx.event;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 微信事件处理
 *
 * @Author dongzhihua
 * @Date 2020-02-10 14:59
 */
@Component
public class MyWxEventDeal extends WxEventDeal {

    @Resource
    ApplicationContext context;

    // 获取事件处理
    @Override
    public EventHandle getEventHandle(String msgType, String event) {
        String beanName = String.format("%s$%s", msgType, event);
        if (context.containsBean(beanName)) {
            return context.getBean(beanName, EventHandle.class);
        }
        return null;
    }
}
