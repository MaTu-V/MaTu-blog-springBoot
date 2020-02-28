package com.mt.blog;

import com.mt.blog.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpSession;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MtWebApplicationTests {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    HttpSession session;
    @Autowired
    RedisUtil redisUtil;

    @Test
    public void contextLoads() throws InterruptedException {
//        Map<String, String> map = new HashMap<>();
//        String key = Constant.RedisCode.SMS_REDIS_CODE_KEY + ": " + PhoneResources.getMsgCode();
//        map.put("phone", "18334967972");
//        map.put("code", "8888");
//        redisTemplate.opsForValue().set("Session1", map, 60, TimeUnit.SECONDS);
//        redisTemplate.opsForValue().set("Session2", map, 60, TimeUnit.SECONDS);
//        List<String> keys = new ArrayList<>();
//        keys.add("Session1");
//        keys.add("Session2");
//        redisUtil.delete(keys);
//        System.out.println( redisUtil.hasKey("Session1"));
//        redisUtil.expire("Session1",20);
//        Thread.sleep(5000);
//        System.out.println(redisUtil.ttl("Session1"));
//        redisUtil.rename("Session1","A");
//        System.out.println(redisUtil.type("Session1"));
//        redisUtil.set("k1","v1");
//        redisUtil.set("k11","v11",60);
//        redisTemplate.opsForValue().set("aa","v",60,TimeUnit.SECONDS);
//        redisUtil.set("k2",map);
//        redisUtil.set("ksss3",map,60);
//        System.out.println(redisUtil.setIfAbsent("Session3","aaa"));
//        System.out.println(redisUtil.setIfAbsent("Session1","z"));
//        System.out.println(redisUtil.size("Session1"));
//        System.out.println(redisUtil.append("Session1","aaaaa"));
//          System.out.println(session.getAttribute(Constant.RedisCode.USER_SESSION_CODE_KEY + ":" + "d7004e81-a87f-4a86-87bc-a6370fa06071"));
    }

}
