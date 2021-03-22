package com.zhangheng.file.controller;

import com.zhangheng.file.entity.Users;
import com.zhangheng.file.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UsersRepository usersRepository;
    @GetMapping("users")
    public List<Users> getUsers(){
        List<Users> users = new ArrayList<>();
        users = usersRepository.findAll();
        return users;
    }
    @PostMapping("users")
    public List<Users> postUsers(){
        List<Users> users = new ArrayList<>();
        users = usersRepository.findAll();
        return users;
    }
    @PostMapping("user")
    public Users addUsers(Users users){
        Users users1 = usersRepository.saveAndFlush(users);
        return users1;
    }
}
