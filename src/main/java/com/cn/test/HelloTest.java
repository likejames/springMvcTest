package com.cn.test;

import com.cn.test.bean.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author : lj
 * @since : 2021/1/22
 */
@RestController
@RequestMapping("/user")
public class HelloTest {

    @PostMapping(path = "/hello",params = {"name"})
    public String hello(){
        System.out.println("入门成功");
        return "success";
    }

    @PostMapping("/testAjax")
    public @ResponseBody Person testAjax(@RequestBody Person person){
        Person person1=new Person();
        person1.setUsername("111");
        System.out.println("testAjax");
        System.out.println(person);
        return person1;
    }

    @PostMapping("/testForm")
    public @ResponseBody Person testForm(@RequestBody String person){
        Person person1=new Person();
        System.out.println("testForm");
        System.out.println(person);
        return person1;
    }
    @PostMapping("ss")
    public void xx(){
        System.out.println("sdsddddd");

    }
}
