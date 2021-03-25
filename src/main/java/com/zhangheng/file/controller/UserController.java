package com.zhangheng.file.controller;

import com.zhangheng.file.entity.Users;
import com.zhangheng.file.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    UsersRepository usersRepository;


    @GetMapping("users")
    public List<Users> getUsers(){
        List<Users> users = new ArrayList<>();
        users = usersRepository.findAll();
        log.info("get查询Users");
        return users;
    }
    @PostMapping("users")
    public List<Users> postUsers(){
        List<Users> users = new ArrayList<>();
        users = usersRepository.findAll();
        log.info("get查询Users");
        return users;
    }
    @PostMapping("user")
    public Users addUsers(Users users){
        Users users1 = usersRepository.saveAndFlush(users);
        log.info("添加Users：<"+users1.getId()+"><"+users1.getName()+"><"+users1.getEmail()+">");
        return users1;
    }
}
