package com.zhangheng.file.controller;

import com.zhangheng.file.entity.PhoneMessage;
import com.zhangheng.file.entity.Result;
import com.zhangheng.file.entity.User;
import com.zhangheng.file.repository.PhoneMessageRepository;
import com.zhangheng.file.util.FolderFileScanner;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
/*
* 遍历上传的文件
*
* */
@Slf4j
@RequestMapping("filelist")
@Controller
public class FileListController {

    Logger log=LoggerFactory.getLogger(getClass());
    @Autowired
    @Qualifier(value = "user")
    private User user;
    @Autowired
    private PhoneMessageRepository phoneMessageRepository;
    ArrayList<Object> files = new ArrayList<>();



    @ResponseBody
    @PostMapping("jsonlist/{type}")
    public ArrayList<String> jsonFileList1(@PathVariable("type") String type,
                                          @Nullable @RequestParam("username") String username,
                                          @Nullable @RequestParam("password") String password){
        ArrayList<String> list = new ArrayList<>();
        list.clear();
        if (username!=null&&password!=null) {
            if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {//设置用户名和密码
                files.clear();
                try {
                    switch (type) {
                        case "all":
                            files = FolderFileScanner.scanFilesWithRecursion("files");
                            break;
                        case "image":
                            files = FolderFileScanner.scanFilesWithRecursion("files\\image");
                            break;
                        case "audio":
                            files = FolderFileScanner.scanFilesWithRecursion("files\\audio");
                            break;
                        case "text":
                            files = FolderFileScanner.scanFilesWithRecursion("files\\text");
                            break;
                        case "video":
                            files = FolderFileScanner.scanFilesWithRecursion("files\\video");
                            break;
                        case "application":
                            files = FolderFileScanner.scanFilesWithRecursion("files\\application");
                            break;
                        case "other":
                            files = FolderFileScanner.scanFilesWithRecursion("files\\other");
                            break;
                        default:
                            files.add("对不起，没有找到你需要的，请换一个！");
                            break;
                    }
                    if (!files.isEmpty()) {
                        for (Object o : files) {
                            String s1 = "";
                            String s = String.valueOf(o);

                            if (s.indexOf("files") > 1) {
                                s1 = s.substring(s.indexOf("files") + 6);
                                s1=s1.replace("\\","/");
                            } else {
                                s1 = s;
                            }
                            list.add(s1);
                        }
                    }
                } catch (Exception e) {
//                    e.printStackTrace();
                    log.error("错误:" + e.getMessage());
                    list.add("错误:" + e.getMessage());
                }
            }else {
                log.warn("账号或密码错误！");
                list.add("账号或密码错误！");
            }
        }else {
            log.warn("请输入账号和密码");
            list.add("请输入账号和密码");
        }
        return list;
    }
    @PostMapping("htmllist")
    public String jsonFileList2(@Nullable @RequestParam("type") String type,
                                          @Nullable @RequestParam("username") String username,
                                          @Nullable @RequestParam("password") String password,
                                           Model model
                                           ){
        ArrayList<String> list = new ArrayList<>();
        list.clear();
        if (username!=null&&password!=null) {
            if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {//设置用户名和密码
                files.clear();
                try {
                    switch (type) {
                        case "all":
                            files = FolderFileScanner.scanFilesWithRecursion("files");
                            break;
                        case "image":
                            files = FolderFileScanner.scanFilesWithRecursion("files\\image");
                            break;
                        case "audio":
                            files = FolderFileScanner.scanFilesWithRecursion("files\\audio");
                            break;
                        case "text":
                            files = FolderFileScanner.scanFilesWithRecursion("files\\text");
                            break;
                        case "video":
                            files = FolderFileScanner.scanFilesWithRecursion("files\\video");
                            break;
                        case "application":
                            files = FolderFileScanner.scanFilesWithRecursion("files\\application");
                            break;
                        case "other":
                            files = FolderFileScanner.scanFilesWithRecursion("files\\other");
                            break;
                        default:
                            files.add("对不起，没有找到你需要的，请换一个！");
                            break;
                    }
                    if (!files.isEmpty()) {
                        for (Object o : files) {
                            String s1 = "";
                            String s = String.valueOf(o);
                            if (s.indexOf("files") > 1) {
                                s1 = s.substring(s.indexOf("files") + 6);
                            } else {
                                s1 = s;
                            }
                            list.add(s1);
                        }
                    }
                } catch (Exception e) {
//                    e.printStackTrace();
                    log.error("错误:" + e.getMessage());
                    list.add("错误:" + e.getMessage());
                }
            }else {
                log.warn("账号或密码错误！");
                list.add("账号或密码错误！");
            }
        }else {
            log.warn("请输入账号和密码");
            list.add("请输入账号和密码");
        }
        model.addAttribute("list",list);
        return "forward:/";
    }


    @PostMapping("updatelist/{type}")
    @ResponseBody
    public Result updatelist(@Nullable @PathVariable("type") String type, PhoneMessage phoneMessage){
        Result result=new Result();
        ArrayList<String> list = new ArrayList<>();
        list.clear();
        if (type.length()>0){
            if (phoneMessage!=null){
                PhoneMessage phoneMessage1 = phoneMessageRepository.saveAndFlush(phoneMessage);
                log.info("用户登录："+phoneMessage1);
            }else {
                log.info("phoneMessage为空");
            }
                try {
                    files.clear();
                    switch (type){
                        case "MyOkHttp":
                            files = FolderFileScanner.scanFilesWithRecursion("update\\MyOkHttp");
                            break;
                            default:
                                files.add("null");
                    }
                    if (!files.isEmpty()) {
                        for (Object o : files) {
                            String s1 = "";
                            String s = String.valueOf(o);
                            if (s.indexOf("update") > 1) {
                                s1 = s.substring(s.indexOf("update"));
                            } else {
                                s1 = s;
                            }
                            s1=s1.replace("\\","/");
                            list.add(s1);
                        }
                        if (list.size()>1){
                            result.setTitle(type);
                            result.setMessage(list.get(list.size()-1));
                        }else {
                            if (list.get(0)=="null"){
                                result.setTitle("null");
                                result.setMessage("没有找到对应的更新");
                            }else {
                                result.setTitle(type);
                                result.setMessage(list.get(0));
                            }
                        }
                    }else {
                        result.setTitle("null");
                        result.setMessage("files为空");
                    }
                    log.info("更新查询title:"+result.getTitle());
                    log.info("更新查询message:"+result.getMessage());

                } catch (Exception e) {
                    if (e.getMessage().indexOf("路径错误或没有此文件夹")>1){
                        result.setTitle("null");
                        result.setMessage(e.getMessage());
                    }else {
                        result.setTitle("错误");
                        result.setMessage(e.getMessage());
                    }
//                    e.printStackTrace();
                    log.error("update错误："+e.getMessage());
                }
        }else {
            result.setTitle("内容为空");
            result.setMessage("请求内容不能为空");
        }
        return result;

    }
}
