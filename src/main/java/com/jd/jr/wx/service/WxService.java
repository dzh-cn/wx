package com.jd.jr.wx.service;

import com.jd.jr.wx.helper.LogHelper;
import org.springframework.stereotype.Service;

@Service
public class WxService {

    @LogHelper.LogReqResp(desc = "说你好")
    public String sayHello(String name) {
        System.out.println("hello " + name);
        return "success";
    }

    @LogHelper.LogReqResp(desc = "啥也不说")
    public String sayHello() {
        System.out.println("嗯...");
        return "success";
    }

    @LogHelper.LogReqResp(desc = "多说几句")
    public String sayHello(String fName, String sName) {
        System.out.println("fName: " + fName + "; sName: " +sName);
        return "success";
    }
}
