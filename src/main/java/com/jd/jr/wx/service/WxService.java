package com.jd.jr.wx.service;

import com.jd.jr.wx.helper.LogHelper;
import org.springframework.stereotype.Service;

@Service
public class WxService {

    @LogHelper.LogReqResp
    public String sayHello(String name) {
        System.out.println("hello " + name);
        return "success";
    }
}
