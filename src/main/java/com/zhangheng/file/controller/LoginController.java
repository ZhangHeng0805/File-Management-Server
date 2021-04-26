package com.zhangheng.file.controller;

import com.zhangheng.file.bean.Merchants;
import com.zhangheng.file.bean.Store;
import com.zhangheng.file.entity.User;
import com.zhangheng.file.repository.MerchantsRepository;
import com.zhangheng.file.repository.StoreRepository;
import com.zhangheng.file.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    UserRepository userRepository;
    Logger log= LoggerFactory.getLogger(getClass());
    @Autowired
    MerchantsRepository merchantsRepository;
    @Autowired
    private StoreRepository storeRepository;

    //登录页面
    @GetMapping(value = {"/","/Login"})
    public String LoginPage(){
        return "login";
    }

    @PostMapping("/Login")
    public String main(User user, HttpSession session, Model model){
        if (user.getUsername().length()>0&&user.getPassword().length()>0) {
            boolean b = merchantsRepository.existsById(user.getUsername());
            if (b) {
                Optional<Merchants> user1 = merchantsRepository.findById(user.getUsername());
                if (user.getPassword().equals(user1.get().getPassword())) {
                    Integer store_id = user1.get().getStore_id();
                    Optional<Store> stores = storeRepository.findById(store_id);
                    //把登陆成功的用户保存起来
                    session.setAttribute("loginUser", user1.get());
                    session.setAttribute("store",stores.get());
                    //登陆成功重定向到index.html页面
                    return "redirect:main.html";
                } else {
                    model.addAttribute("msg", "密码错误");
                    //回到登录页
                    return "login";
                }
            }else {
                model.addAttribute("msg","没有此用户");
                return "login";
            }
        }else{
            model.addAttribute("msg","账号为空");
            return "login";
        }

    }
    //去main页面
    @GetMapping("main.html")
    public String mainPage(Model model){
        //判断是否登录，拦截器，过滤器
//        Object loginUser = session.getAttribute("loginUser");
//        if (loginUser!=null){
//            return "index";
//        }else {
//            model.addAttribute("msg","请重新登录");
//            return "login";
//        }
        model.addAttribute("active","main");
        return "main";
    }
    @RequestMapping("/registration")
    public String registrationPage(){
//        log.info("跳转到注册");
        return "registration";
    }

    @RequestMapping("/exit")
    public String exit(HttpServletRequest request){
        Enumeration em = request.getSession().getAttributeNames();
        while(em.hasMoreElements()){
            request.getSession().removeAttribute(em.nextElement().toString());
        }
        return "redirect:Login";
    }
}
