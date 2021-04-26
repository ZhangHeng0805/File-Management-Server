package com.zhangheng.file.controller;

import com.zhangheng.file.entity.ChatConfig;
import com.zhangheng.file.repository.ChatConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequestMapping("config")
@RestController
public class ConfigController {

    Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private ChatConfigRepository chatConfigRepository;

    @GetMapping("chatconfig")
    public ChatConfig chatConfig(@RequestParam("port")String port){
        ChatConfig chatConfig = new ChatConfig();
        Optional<ChatConfig> byId = chatConfigRepository.findById(port);
        if (byId.isPresent()){
            chatConfig=byId.get();
        }else {
            log.error("chatconfig错误："+port+"查询为空");
        }
        log.info("聊天室配置："+chatConfig);
        return chatConfig;
    }
}
