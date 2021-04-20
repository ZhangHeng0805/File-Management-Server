package com.zhangheng.file.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TableController {

    @GetMapping("basic_table")
    public String basic_table(Model model){
        model.addAttribute("active","1");
        return "table/basic_table";
    }
    @GetMapping("dynamic_table")
    public String dynamic_table(Model model){
        model.addAttribute("active","2");
        return "table/dynamic_table";
    }
    @GetMapping("responsive_table")
    public String responsive_table(Model model){
        model.addAttribute("active","3");
        return "table/responsive_table";
    }

}
