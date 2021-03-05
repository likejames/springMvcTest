package com.cn.test;

import com.cn.test.config.BeanConfig;
import com.cn.test.config.JedisClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanConfig.class);
        JedisClient jedisClient = applicationContext.getBean("jedis", JedisClient.class);
        jedisClient.setex("ss", "ss", 60*5);
        Long ttl = jedisClient.expire("ss");
        System.out.println("ttl: "+ttl);
        Long expire = 60*4L;
        if (ttl > expire ) {
            System.out.println(1);
        }else {
            System.out.println(2);
        }
    }
}
