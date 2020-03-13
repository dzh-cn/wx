package com.jd.jr.wx;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.net.URLEncoder;

@SpringBootTest
class WxApplicationTests {

    @Resource
    RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
        System.out.println(redisTemplate.opsForValue().get("name"));
    }

    public static void main(String[] args) {
        System.out.println(URLEncoder.encode("http://hsp.jdpay.com/?appid=92&filepath=/temp/2.gif&auth=PaDLTFCTTPlosi5lRKUqp5KWhs5hPTkyJmU9MTk4MzQwNjMxMSZ0PTE1ODM0MDYzMTImZj04OV8yMDAzXzU3MF83OSZyPVNaWWw3MVhrdVE="));
    }
}
