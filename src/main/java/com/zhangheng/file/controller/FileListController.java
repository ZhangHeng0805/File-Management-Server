package com.zhangheng.file.controller;

import com.zhangheng.file.entity.Result;
import com.zhangheng.file.entity.User;
import com.zhangheng.file.util.FolderFileScanner;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
/*
* 遍历上传的文件
*
* */
@Slf4j
@RequestMapping("filelist")
@RestController
public class FileListController {

    Logger log=LoggerFactory.getLogger(getClass());
    @Autowired
    private User user;
    ArrayList<Object> files = new ArrayList<>();

    @PostMapping("jsonlist/{type}")
    public ArrayList<String> jsonFileList1(@PathVariable("type") @RequestParam("type") String type,
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
    @PostMapping("jsonlist")
    public ArrayList<String> jsonFileList2(@Nullable @RequestParam("type") String type,
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
}
