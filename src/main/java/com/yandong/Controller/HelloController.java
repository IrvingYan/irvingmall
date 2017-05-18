package com.yandong.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by yandong on 2017/5/18.
 */

@Controller
@RequestMapping(value = "/hello/")
public class HelloController {

    @RequestMapping(value = "hello.do",method = RequestMethod.GET)
    @ResponseBody
    public String hello(){
        return "hello world";
    }
}
