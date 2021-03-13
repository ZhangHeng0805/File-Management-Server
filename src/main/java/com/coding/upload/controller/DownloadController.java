package com.coding.upload.controller;

import com.coding.upload.util.CusAccessObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


@RequestMapping("downloads")
@RestController
public class DownloadController {
    private static String baseDir = "files/";
    Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("download")
    public void download(String name, HttpServletResponse response, Model model)  {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
//        response.setContentType("text/html,charset=UTF-8");
//        String n=name.substring(20);
        File file = new File(baseDir + name);
        try {
            IOUtils.copy(FileUtils.openInputStream(file), response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("err1","错误："+e.getMessage());
        }
    }

    @GetMapping("show/{time}/{name:.+}")
    public void show(@PathVariable("name") String name,
                     @PathVariable("time") String time, HttpServletRequest request,
                     HttpServletResponse response) {
        String user_agent = CusAccessObjectUtil.getUser_Agent(request);
        String ipAddress = CusAccessObjectUtil.getIpAddress(request);
        log.info("下载请求头："+user_agent);
        log.info("下载IP："+ipAddress);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        File file = new File(baseDir + time+"/"+name);
        FileInputStream input = null;
        try {
            input = FileUtils.openInputStream(file);
            IOUtils.copy(input, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
