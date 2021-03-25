package com.zhangheng.file;

import com.zhangheng.file.entity.PhoneMessage;
import com.zhangheng.file.entity.S3Config;
import com.zhangheng.file.entity.User;
import com.zhangheng.file.repository.PhoneMessageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class DemoUploadApplicationTests {

    @Autowired
    User user;
    @Autowired
    S3Config s3Config;
    @Test
    void contextLoads() {
        System.out.println(user);
        System.out.println(s3Config);
        System.out.println("_____________");
        try {
//            ArrayList<Object> files = FolderFileScanner.scanFilesWithNoRecursion("files");
//            ArrayList<Object> files1 = FolderFileScanner.scanFilesWithRecursion("files");
//            System.out.println(files);
            System.out.println("_____________");
//            System.out.println(files1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Autowired
    PhoneMessageRepository phoneMessageRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Test
    public void test1(){
        PhoneMessage phoneMessage=new PhoneMessage();
        phoneMessage.setPhonenum("1qq");
        phoneMessage.setModel("111");
        phoneMessage.setSdk("123");
        phoneMessage.setRelease("123");
        phoneMessage.setTime("123");
//        phoneMessageRepository.saveAndFlush(phoneMessage);
//        List<PhoneMessage> all = phoneMessageRepository.findAll();
//        System.out.println(all);

    }


}
