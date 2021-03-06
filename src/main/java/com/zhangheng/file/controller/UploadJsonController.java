package com.zhangheng.file.controller;

import com.zhangheng.file.entity.Result;
import com.zhangheng.file.entity.User;
import com.zhangheng.file.util.FiletypeUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author guanweiming
 */
//@Slf4j
@RequestMapping("uploadjson")
@RestController
public class UploadJsonController {
    @Autowired
    User user;
    private static String baseDir = "files/";

    Logger log = LoggerFactory.getLogger(getClass());


    @PostMapping("files")
    public Result upload(MultipartFile file,
                         @Nullable @RequestParam("username") String username,
                         @Nullable @RequestParam("password") String password)  {
        String tit="";
        String msg="";
        Result result=new Result();
        String fileType = FiletypeUtil.getFileType(file.getOriginalFilename());
        if (username!=null&&password!=null){
        if (username.equals(user.getUsername())&&password.equals(user.getPassword())){//设置用户名和密码
        if (!file.isEmpty()) {
            String filename=file.getOriginalFilename();
            log.info("文件名：{}", filename);
            log.info("大小：{}字节", file.getSize());
//             String name= /*UUID.randomUUID().toString()*/
//                     new SimpleDateFormat("yyyy-MM-dd/HH-mm-ss_SSS").format(new Date())
//                             + "@" + file.getOriginalFilename();
            filename = filename
                    .replace(" ", "")
                    .replace("星曦向荣网", "")
                    .replace("星曦向荣", "")
            ;
            String name=fileType+"/"
                    +"星曦向荣网"
                    + UUID.randomUUID().toString().substring(0,5)
                    + "_" + filename;
            File outFile = new File(baseDir + name);

            try {
                FileUtils.copyToFile(file.getInputStream(), outFile);
                tit="上传成功！";
                msg=name;
            } catch (IOException e) {
                e.printStackTrace();
                tit="错误！";
                msg=e.getMessage();
            }
        }else {
            tit="上传失败！";
            msg="文件为空";
        }
        } else {
            tit="账号错误";
            msg="用户名或密码错误！";
        }}else {
            tit="账号为空";
            msg="用户名或密码不能为空！";
        }
        result.setTitle(tit);
        result.setMessage(msg);
        log.info("上传结果标题："+tit);
        log.info("上传结果信息："+msg);
        return result;
    }


}
