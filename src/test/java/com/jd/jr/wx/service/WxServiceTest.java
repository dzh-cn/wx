package com.jd.jr.wx.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WxServiceTest {
    @Resource
    WxService wxService;
    @Test
    void sayHello() {

//        System.out.println(wxService.sayHello("dong"));
        System.out.println(wxService.sayHello());
//        System.out.println(wxService.sayHello("dong", "zhihua"));
    }
}