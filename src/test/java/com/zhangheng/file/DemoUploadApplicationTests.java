package com.zhangheng.file;

import com.zhangheng.file.entity.S3Config;
import com.zhangheng.file.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    }


}
