package com.coding.upload.controller;

import com.coding.upload.entity.Result;
import com.coding.upload.util.FiletypeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author guanweiming
 */
//@Slf4j
@RequestMapping("uploadjson")
@RestController
public class UploadJsonController {

    private static String baseDir = "files/";

    Logger log = LoggerFactory.getLogger(getClass());


    @PostMapping("files")
    public Result upload(MultipartFile file,
                        @Nullable @RequestParam("username") String username,
                         @Nullable @RequestParam("password") String password)  {
        String msg="";
        Result result=new Result();
        String fileType = FiletypeUtil.getFileType(file.getOriginalFilename());
        if (username!=null&&password!=null){
        if (username.equals("星曦向荣")&&password.equals("305666")){//设置用户名和密码
        if (!file.isEmpty()) {
            log.info("文件名：{}", file.getOriginalFilename());
            log.info("大小：{}字节", file.getSize());
//             String name= /*UUID.randomUUID().toString()*/
//                     new SimpleDateFormat("yyyy-MM-dd/HH-mm-ss_SSS").format(new Date())
//                             + "@" + file.getOriginalFilename();
            String name=fileType+"/"
                    +"星曦向荣网"
                    + UUID.randomUUID().toString().substring(0,5)
                    + "@" + file.getOriginalFilename();
            File outFile = new File(baseDir + name);

            try {
                FileUtils.copyToFile(file.getInputStream(), outFile);
                msg="上传成功！";
                result.setMessage(name);
            } catch (IOException e) {
                e.printStackTrace();
                msg="错误："+e.getMessage();
            }
        }else {
            msg="上传失败！文件为空";
        }
        } else {
            msg="用户名或密码错误！";
        }}else {
            msg="用户名或密码不能为空！";
        }
        result.setTitle(msg);
        log.info("上传结果："+msg);
        return result;
    }


}
