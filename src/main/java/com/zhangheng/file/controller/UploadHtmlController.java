package com.zhangheng.file.controller;

import com.zhangheng.file.entity.User;
import com.zhangheng.file.util.FiletypeUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RequestMapping("uploadhtml")
@Controller
public class UploadHtmlController {
    private static String baseDir = "files/";
    @Autowired
    User user;

    Logger log = LoggerFactory.getLogger(getClass());



    @PostMapping("files")
    public String upload(MultipartFile file, Model model,
                         @Nullable @RequestParam("username") String username,
                         @Nullable @RequestParam("password") String password)  {
        String msg="";
        if (username!=null&&password!=null){
            if (username.equals(user.getUsername())&&password.equals(user.getPassword())){//设置上传的用户名和密码
                if (!file.isEmpty()) {
                    String filename=file.getOriginalFilename();
                    String fileType = FiletypeUtil.getFileType(filename);
                    log.info("文件名：{}", file.getOriginalFilename());
                    log.info("文件类型：{}", fileType);
                    log.info("大小：{}字节", file.getSize());
//                    String name= /*UUID.randomUUID().toString()*/
//                            new SimpleDateFormat("yyyy-MM-dd/HH-mm-ss_SSS").format(new Date())
//                                    + "@" + file.getOriginalFilename();
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

                        msg="上传成功！";

                        model.addAttribute("name",name);
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
        model.addAttribute("msg",msg);
        log.info("上传结果："+msg);
        //redirect:表示从重定向
        //forward:表示转发到一个地址
        return "forward:/";
    }
}
