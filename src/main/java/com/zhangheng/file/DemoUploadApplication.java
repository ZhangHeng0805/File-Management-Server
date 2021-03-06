package com.zhangheng.file;

import com.zhangheng.file.chat.ChatServer;
import com.zhangheng.file.entity.Delete_Image;
import com.zhangheng.file.repository.DeleteImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.File;
import java.util.List;

@SpringBootApplication
@EntityScan("com.zhangheng.file")
public class DemoUploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoUploadApplication.class, args);
//        ChatServer server=new ChatServer(8888);
        com.zhangheng.file.chat.ShopChat.ChatServer server1=new com.zhangheng.file.chat.ShopChat.ChatServer(8889);
//        server.run();
        server1.run();

    }

}
