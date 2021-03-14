package com.zhangheng.file.controller;

import io.minio.MinioClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author guanweiming
 */
//@Slf4j
//@AllArgsConstructor
@RequestMapping("minio")
@RestController
public class MinioController {

    @Autowired
    private MinioClient minioClient;



    Logger log = LoggerFactory.getLogger(getClass());



    @PostMapping("files")
    public String upload(MultipartFile file) throws Exception {
        log.info("getOriginalFilename:{}", file.getOriginalFilename());
        log.info("getSize:{}", file.getSize());
        String name = new SimpleDateFormat("yyyy-MM-dd/HH-mm-ss_SSS").format(new Date())
                + "@" + file.getOriginalFilename();
        // 检查存储桶是否已经存在
        boolean isExist = minioClient.bucketExists("files");
        if (isExist) {
            log.info("Bucket already exists.");
        } else {
            // 创建一个存储桶，用于存储文件。
            minioClient.makeBucket("files");
        }
        // 使用putObject上传一个文件到存储桶中。
        minioClient.putObject("files", name, file.getInputStream(), file.getContentType());
        return name;
    }


}
