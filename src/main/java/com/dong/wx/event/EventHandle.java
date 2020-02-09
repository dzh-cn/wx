package com.dong.wx.event;

import java.util.Map;

/**
 * 通知消息处理器
 *
 * @Author dongzhihua
 * @Date 2020-02-07 10:59
 */
public interface EventHandle<T> {
    /**
     * 事件通知
     * @Author dongzhihua
     * @Date 2020-02-07 11:00
     */
    boolean notice(T msg);
}
