package com.zhangheng.file.controller;

import com.zhangheng.file.entity.Result;
import com.zhangheng.file.util.CusAccessObjectUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;


@RequestMapping("downloads")
@RestController
public class DownloadController {
    private static String baseDir = "files/";
    Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    MyExceptionHandler myExceptionHandler;

    @GetMapping("download")
    public void download(String name, HttpServletResponse response, Map<String,String> model, HttpServletRequest request) throws IOException {
        log.info("下载请求头："+CusAccessObjectUtil.getUser_Agent(request));
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        File file = new File(baseDir + name);
        try {
            response.setHeader("Content-Length", String.valueOf(file.length()));
            IOUtils.copy(FileUtils.openInputStream(file), response.getOutputStream());
            log.info("下载IP："+CusAccessObjectUtil.getIpAddress(request));
            log.info("下载请求成功(download)");
        } catch (IOException e) {
//            e.printStackTrace();
            log.error("错误："+e.getMessage());
            if (e.getMessage().indexOf("does not exist")>1) {
//                request.setAttribute("javax.servlet.error.status_code",404);
                response.sendError(404,e.getMessage());
            }else {
//                request.setAttribute("javax.servlet.error.status_code",500);
                response.sendError(500,e.toString());
            }
            model.put("err1","错误："+e.getMessage());
        }
    }

    @GetMapping("show/{time}/{name:.+}")
    public Result show(@PathVariable("name") String name,
                       @PathVariable("time") String time, HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        Result result=new Result();
        String user_agent = CusAccessObjectUtil.getUser_Agent(request);
        String ipAddress = CusAccessObjectUtil.getIpAddress(request);
        log.info("下载请求头："+user_agent);
        log.info("下载IP："+ipAddress);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        File file = new File(baseDir + time+"/"+name);
        log.info("下载文件："+file.getPath());
        FileInputStream input = null;
        try {
            response.setHeader("Content-Length", String.valueOf(file.length()));
            input = FileUtils.openInputStream(file);
            IOUtils.copy(input, response.getOutputStream());
            result.setTitle("请求成功");
            result.setMessage(file.getName());
            log.info("下载请求成功(show)");
        } catch (IOException e) {
            if (e.toString().indexOf("does not exist")>1){
                result.setTitle("错误:404");
                response.sendError(404);
            }else {
                result.setTitle("错误");
            }
            log.error("错误："+e.getMessage());
            result.setMessage(e.getMessage());
//            e.printStackTrace();
        }
        return result;
    }
    @ResponseBody
    @GetMapping("downupdate/update/{type}/{name}")
    public Result downupdate(@Nullable @PathVariable("type") String type,@Nullable @PathVariable("name") String name,HttpServletResponse response){
        Result result=new Result();
        if (type.length()>0){
            File file = new File("update/"+type+"/"+name);
            log.info("更新下载："+file.getPath());
            FileInputStream input = null;
            try {
                response.setHeader("Content-Length", String.valueOf(file.length()));
                input = FileUtils.openInputStream(file);
                IOUtils.copy(input, response.getOutputStream());
                result.setTitle("请求成功");
                result.setMessage(file.getName());
                log.info("更新下载请求成功");
            } catch (IOException e) {
                if (e.getMessage().indexOf("does not exist")>1){
                    result.setTitle("错误:404（没找到）");
                }else {
                    result.setTitle("错误");
                }
                log.error("错误："+e.getMessage());
                result.setMessage(e.getMessage());
            }
        }else {
            result.setTitle("内容为空");
            result.setMessage("请求内容不能为空");
        }

        return result;
    }
}
