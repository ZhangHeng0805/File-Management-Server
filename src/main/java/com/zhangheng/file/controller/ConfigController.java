package com.zhangheng.file.controller;

import com.zhangheng.file.entity.ChatConfig;
import com.zhangheng.file.repository.ChatConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("config")
@RestController
public class ConfigController {

    Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private ChatConfigRepository chatConfigRepository;

    @GetMapping("chatconfig")
    public ChatConfig chatConfig(){
        ChatConfig chatConfig = new ChatConfig();
        List<ChatConfig> all = chatConfigRepository.findAll();
        if (all.size()>0){
            if (all.size()!=1){
                chatConfig=all.get(all.size()-1);
            }else {
                chatConfig=all.get(0);
            }
        }else {
            log.error("chatconfig错误：查询为空");
        }
        return chatConfig;
    }
}
