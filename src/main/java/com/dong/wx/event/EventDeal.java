package com.dong.wx.event;

import com.dong.wx.utils.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * 事件处理
 *
 * @Author dongzhihua
 * @Date 2020-02-07 11:18
 */
@Component
public class EventDeal {

    private static Logger log = LoggerFactory.getLogger(EventDeal.class);

    @Resource
    ApplicationContext context;

    protected EventHandle getEventHandle(String msgType, String event) {
        String beanName = String.format("%s$%s", msgType, event);
        log.info("getEventHandle beanName: {}", beanName);
        if (context.containsBean(beanName)) {
            return context.getBean(beanName, EventHandle.class);
        }
        return null;
    }

    /**
     * 处理事件
     * @Author dongzhihua
     * @Date 2020-02-07 11:42
     */
    public boolean deal(Map<String, String> msg) {
        String msgType = msg.get("msgType");
        String event = StringUtils.trimToEmpty(msg.get("event"));

        EventHandle ha = getEventHandle(msgType, event);
        if (ha == null) {
            log.warn("deal 事件处理器未正确注册或者未实现：msgType: {}, event: {}, msg: {}", msgType, event, msg);
            return true;
        }

        Method[] methods = ha.getClass().getDeclaredMethods();

        Type type = null;
        for (Method dm : methods) {
            if ("notice".equals(dm.getName())) {
                Type t = dm.getGenericParameterTypes()[0];
                if (!t.equals(Object.class)) {
                    type = t;
                }
            }
        }

        ha.notice(JsonUtils.mapToBean(msg, (Class) type));
        return true;
    }
}
