package com.zhangheng.file.controller;

import com.zhangheng.file.entity.Location;
import com.zhangheng.file.entity.Resuilt;
import com.zhangheng.file.entity.User;
import com.zhangheng.file.repository.LocationRepository;
import com.zhangheng.file.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class locationController {
        @Autowired
                @Qualifier(value = "userRepository")
        UserRepository userRepository;
        @Autowired
        @Qualifier(value = "locationRepository")
        LocationRepository locationRepository;

    @PostMapping("zhuce")
    public Resuilt zhuce(User user){
        Resuilt resuilt=new Resuilt();
        if (user!=null) {
            boolean b = userRepository.existsById(user.getUsername());
            if (!b) {
                User save = userRepository.save(user);
                resuilt.setTitle("注册成功");
                resuilt.setMessage(user.getUsername()+"：恭喜注册成功");
            }else {
                resuilt.setTitle("数据重复");
                resuilt.setMessage("该账号已经存在");
            }
        }else {
            resuilt.setTitle("数据为空");
            resuilt.setMessage("账号密码不能为空");
        }
        return resuilt;
    }
    @PostMapping("login")
    public Resuilt login(User user){
        Resuilt resuilt=new Resuilt();
        if (user!=null) {
            boolean b = userRepository.existsById(user.getUsername());
            if (b) {
                User user1 = userRepository.findById(user.getUsername()).get();
                if (user.getUsername().equals(user1.getUsername()) && user.getPassword().equals(user1.getPassword())) {
                    resuilt.setTitle("登录成功");
                    resuilt.setMessage(user.getUsername());
                } else {
                    resuilt.setTitle("账号错误");
                    resuilt.setMessage("账号密码错误！");
                }
            }else {
                resuilt.setTitle("账号错误");
                resuilt.setMessage("此账号不存在！");
            }
        }else {
            resuilt.setTitle("数据为空");
            resuilt.setMessage("账号密码不能为空");
        }
        return resuilt;
    }

    @PostMapping("location")
    public List<Location> location(Location location){
        if (location!=null) {
            Location save = locationRepository.saveAndFlush(location);
            List<Location> locations = new ArrayList<>();
            locations = locationRepository.findAll();
            return locations;
        }else {return null;}
    }

    @GetMapping("yanzheng")
    public boolean yanzheng(){
        return true;
    }

}
