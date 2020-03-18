package com.jd.jr.wx.config;

import com.jd.jr.wx.helper.LogHelper;
import org.springframework.beans.factory.annotation.Configurable;

import javax.annotation.Resource;

@Configurable
public class LogConfig {

    @Resource
    public LogHelper getBean() {
        return new LogHelper();
    }
}
