package com.coding.upload.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author guanweiming
 */
//@Slf4j
//@AllArgsConstructor
@RequestMapping("s3")
@RestController
public class S3Controller {

    @Autowired
    private AmazonS3 s3;
    Logger log = LoggerFactory.getLogger(getClass());

    @PostMapping("files")
    public String upload(MultipartFile file) throws Exception {
        String name = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        String bucketName = "s3files";
        // 检查存储桶是否已经存在
        boolean isExist = s3.doesBucketExistV2(bucketName);
        if (isExist) {
            log.info("Bucket already exists.");
        } else {
            // 创建一个存储桶，用于存储文件。
            s3.createBucket(bucketName);
        }

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        s3.putObject(bucketName, name, file.getInputStream(), metadata);
        return name;
    }


}
