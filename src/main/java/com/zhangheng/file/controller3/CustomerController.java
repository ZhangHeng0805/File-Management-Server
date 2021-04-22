package com.zhangheng.file.controller3;

import com.google.gson.Gson;
import com.zhangheng.file.bean.Customer;
import com.zhangheng.file.entity.Result;
import com.zhangheng.file.entity.User;
import com.zhangheng.file.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequestMapping("Customer")
@RestController
public class CustomerController {
    private Logger log= LoggerFactory.getLogger(getClass());
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("Login")
    public Result Login(@Nullable @RequestParam("CustomerLogin") String loginJson){
        Result result = new Result();
        if (loginJson.length()>5){
            Gson gson = new Gson();
            User user = gson.fromJson(loginJson, User.class);
            if (user.getUsername().length()==11){
                if (user.getPassword().length()>=6&&user.getPassword().length()<=18){
                    Optional<Customer> customer = customerRepository.findById(user.getUsername());
                    if (customer.isPresent()){
                        if (customer.get().getPassword().equals(user.getPassword())){
                            result.setTitle("登录成功");
                            result.setMessage(customer.get().getUsername());
                        }else {
                            result.setTitle("登录失败");
                            result.setMessage("密码错误");
                        }
                    }else {
                        result.setTitle("登录失败");
                        result.setMessage("用户不存在");
                    }

                }else {
                    result.setTitle("登录失败");
                    result.setMessage("密码格式错误");
                }
            }else {
                result.setTitle("登录失败");
                result.setMessage("账号格式错误");
            }
        }else {
            result.setTitle("登录失败");
            result.setMessage("服务器没有收到注册内容");
        }
        log.info(result.toString());
        return result;
    }

    @PostMapping("getCustomer")
    public Customer getCustomer(@Nullable User user){
        Customer customer = new Customer();
        if (user!=null) {
            Optional<Customer> byId = customerRepository.findById(user.getUsername());
            if (byId.isPresent()) {
                if (byId.get().getPassword().equals(user.getPassword())){
                    customer=byId.get();
                }else {
                    log.info("密码错误");
                }
            }else {
                log.info("用户不存在");
            }
        }else {
            log.info("user为空");
        }
        return customer;
    }

    @PostMapping("updateUsername")
    public Result updateUsername(@Nullable User user){
        Result result = new Result();
        if (user!=null) {
            boolean b = customerRepository.existsById(user.getUsername());
            if (b) {
                customerRepository.updateUserNameByPhone(user.getPassword(),user.getUsername());
                result.setTitle("修改成功");
                result.setMessage(user.getPassword());
            }else {

                result.setTitle("用户不存在");
                result.setMessage("对不起没有此账户的信息");
            }
        }else {

            result.setTitle("参数为空");
            result.setMessage("服务器没有收到请求参数");
        }
        log.info(result.toString());
        return result;
    }
    @PostMapping("updateIcon")
    public Result updateIcon(@Nullable User user){
        Result result = new Result();
        if (user!=null) {
            boolean b = customerRepository.existsById(user.getUsername());
            if (b) {
                customerRepository.updateIconByPhone(user.getPassword(),user.getUsername());
                result.setTitle("修改成功");
                result.setMessage(user.getPassword());
            }else {
                result.setTitle("用户不存在");
                result.setMessage("对不起没有此账户的信息");
            }
        }else {

            result.setTitle("参数为空");
            result.setMessage("服务器没有收到请求参数");
        }
        log.info(result.toString());
        return result;
    }
    @PostMapping("updatePassWord")
    public Result updatePassWord(@Nullable User user){
        Result result = new Result();
        if (user!=null) {
            boolean b = customerRepository.existsById(user.getUsername());
            if (b) {
                customerRepository.updatePassWordByPhone(user.getPassword(),user.getUsername());
                result.setTitle("修改成功");
                result.setMessage(user.getPassword());
            }else {
                result.setTitle("用户不存在");
                result.setMessage("对不起没有此账户的信息");
            }
        }else {

            result.setTitle("参数为空");
            result.setMessage("服务器没有收到请求参数");
        }
        log.info(result.toString());
        return result;
    }
    @PostMapping("setAddress")
    public Result setAddress(@Nullable @RequestParam("address") String address,
                             @Nullable @RequestParam("phone") String phone){
        Result result=new Result();
        if (phone.length()==11) {
            if (address.length() > 3) {
                boolean b = customerRepository.existsById(phone);
                if (b){
                    customerRepository.updateAddressByPhone(address,phone);
                    result.setTitle("保存成功");
                    result.setMessage("地址保存成功");
                }else {
                    result.setTitle("用户不存在");
                    result.setMessage("该账号的用户不存在");
                }

            } else {
                result.setTitle("地址为空");
                result.setMessage("地址过短或为空");
            }
        }else {
            result.setTitle("手机号格式错误");
            result.setMessage("手机号的长度错误");
        }

        return result;
    }
}
