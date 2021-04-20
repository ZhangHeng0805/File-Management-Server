package com.zhangheng.file.controller;

import org.apache.tomcat.util.file.ConfigurationSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class IndexController {
    @RequestMapping ("/file")
    public String index1(){
        return "index";
    }

}
