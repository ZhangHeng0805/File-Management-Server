package com.zhangheng.file.controller;

import com.zhangheng.file.bean.Goods;
import com.zhangheng.file.bean.GoodsOrder;
import com.zhangheng.file.bean.Merchants;
import com.zhangheng.file.bean.Store;
import com.zhangheng.file.bean.submitgoods.SubmitGoods;
import com.zhangheng.file.bean.submitgoods.goods;
import com.zhangheng.file.entity.User;
import com.zhangheng.file.repository.*;
import com.zhangheng.file.util.DigitalFormatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
    @Autowired
    private SubmitGoodsRepository submitGoodsRepository;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private ListGoodsRepository listGoodsRepository;

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
    public String mainPage(HttpServletRequest request,Model model){
        HttpSession session =request.getSession();
        Store store = (Store) session.getAttribute("store");
        List<Goods> goodsList = goodsRepository.findByStore_id(store.getStore_id());
        List<goods> byStore_id = listGoodsRepository.findByStore_idAndState(store.getStore_id(),"未处理");
        List<goods> OKGoodList = listGoodsRepository.findByStore_idAndState(store.getStore_id(),"已收货");
        List<GoodsOrder> submitGoodsList=new ArrayList<>();
        int goodsnum=0;
        double goodsprice=0;
        for (goods g:byStore_id) {
            GoodsOrder goodsOrder = new GoodsOrder();
            String list_id = g.getList_id();
            SubmitGoods submitId = submitGoodsRepository.findBySubmit_id(list_id);
            goodsOrder.setSubmit_id(submitId.getSubmit_id());
            goodsOrder.setName(submitId.getName());
            goodsOrder.setPhone(submitId.getPhone());
            goodsOrder.setAddress(submitId.getAddress());
            goodsOrder.setTime(submitId.getTime());
            goodsOrder.setGoods_name(g.getGoods_name());
            goodsOrder.setGoods_id(g.getGoods_id());
            goodsOrder.setGoods_price(g.getGoods_price());
            goodsOrder.setNum(g.getNum());
            submitGoodsList.add(goodsOrder);
            goodsnum+=g.getNum();
            goodsprice+=g.getGoods_price()*g.getNum();
        }
        int num = 0;
        double count_price = 0;
        for (Goods g:goodsList){
            num+=g.getGoods_month_much();
            count_price+=g.getGoods_price()*g.getGoods_month_much();
        }
        count_price= DigitalFormatUtil.formatDouble2(count_price);
        goodsprice= DigitalFormatUtil.formatDouble2(goodsprice);
//        log.info("店铺："+store.getStore_name()+";总销量："+num+";总营业额："+count_price);
//        log.info("订单数量:"+submitGoodsList.size());
        model.addAttribute("num",num);
        model.addAttribute("OKnum",OKGoodList.size());
        model.addAttribute("goodsnum",goodsnum);
        model.addAttribute("listnum",submitGoodsList.size());
        model.addAttribute("count_price",count_price);
        model.addAttribute("goodsprice",goodsprice);
        model.addAttribute("Store_name",store.getStore_name());
        model.addAttribute("Store_introduce",store.getStore_introduce());
        model.addAttribute("count_price",count_price);
        model.addAttribute("submitGoodsList",submitGoodsList);
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
