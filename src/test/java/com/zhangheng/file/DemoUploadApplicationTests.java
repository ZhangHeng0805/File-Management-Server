package com.zhangheng.file;

import com.zhangheng.file.entity.S3Config;
import com.zhangheng.file.entity.User;
import com.zhangheng.file.util.FolderFileScanner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

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
            ArrayList<Object> files = FolderFileScanner.scanFilesWithNoRecursion("files");
//            ArrayList<Object> files1 = FolderFileScanner.scanFilesWithRecursion("files");
            System.out.println(files);
            System.out.println("_____________");
//            System.out.println(files1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
