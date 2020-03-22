package com.jd.jr.wx.service;

import com.jd.jr.wx.helper.Log;
import org.springframework.stereotype.Service;

@Service
public class WxService {

    @Log(m = "说你好")
    public String sayHello(String name) {
        System.out.println("hello " + name);
        return "success";
    }
}
