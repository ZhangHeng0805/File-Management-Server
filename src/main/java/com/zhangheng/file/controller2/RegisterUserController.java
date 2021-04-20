package com.zhangheng.file.controller2;

import com.google.gson.Gson;
import com.zhangheng.file.bean.Customer;
import com.zhangheng.file.entity.Result;
import com.zhangheng.file.repository.CustomerRepository;
import com.zhangheng.file.util.FolderFileScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/*
* 手机用户注册
* */
@RequestMapping("RegisterCustomer")
@RestController
public class RegisterUserController {
    private Logger log= LoggerFactory.getLogger(getClass());
    @Autowired
    private CustomerRepository customerRepository;
    private ArrayList<Object> list = new ArrayList<>();

    @GetMapping("customericonlist")
    public List<String> UserIcon(){
        ArrayList<String> iconList=new ArrayList<>();
        iconList.clear();
        try {
            list.clear();
            list= FolderFileScanner.scanFilesWithNoRecursion("files\\customer");
            for (Object o:list){
                String s1="";
                String s= (String) o;
                if (s.indexOf("files") > 1) {
                    s1 = s.substring(s.indexOf("files") + 6);
                } else {
//                    s1 = s;
                }
                s1 = s1.replace("\\", "/");
                if (s1.length()>8) {
                    iconList.add(s1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iconList;
    }

    @PostMapping("register")
    public Result zhuce(@Nullable @RequestParam("customerJson") String json){
        Result result = new Result();
        if (json.length()>5) {
            Gson gson = new Gson();
            Customer customer = gson.fromJson(json, Customer.class);
            if (customer.getPhone().length() > 0 && customer.getPassword().length() > 0) {
                boolean b = customerRepository.existsById(customer.getPhone());
                if (!b) {
                    Customer save = customerRepository.save(customer);
                    result.setTitle("注册成功");
                    result.setMessage("恭喜：" + save.getUsername() + "注册成功！");
                } else {
                    result.setTitle("注册失败");
                    result.setMessage("此手机号码已存在");
                }
            } else {
                result.setMessage("注册内容为空");
                result.setTitle("注册失败");
            }
        }else {
            result.setTitle("注册失败");
            result.setMessage("服务器没有收到注册内容");
        }
        log.info(result.toString());
        return result;
    }
}
