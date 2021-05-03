package com.zhangheng.file.controller;

import com.zhangheng.file.bean.Goods;
import com.zhangheng.file.bean.GoodsOrder;
import com.zhangheng.file.bean.Store;
import com.zhangheng.file.bean.submitgoods.SubmitGoods;
import com.zhangheng.file.bean.submitgoods.goods;
import com.zhangheng.file.repository.GoodsRepository;
import com.zhangheng.file.repository.ListGoodsRepository;
import com.zhangheng.file.repository.SubmitGoodsRepository;
import com.zhangheng.file.util.DigitalFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TableController {
    @Autowired
    private SubmitGoodsRepository submitGoodsRepository;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private ListGoodsRepository listGoodsRepository;
    @GetMapping("basic_table")
    public String basic_table(HttpServletRequest request,Model model){
        HttpSession session =request.getSession();
        Store store = (Store) session.getAttribute("store");
        List<Goods> goodsList = goodsRepository.findByStore_id(store.getStore_id());
        List<goods> byStore_id = listGoodsRepository.findByStore_id(store.getStore_id());
        List<GoodsOrder> submitGoodsList=new ArrayList<>();
        /*int goodsnum=0;
        double goodsprice=0;*/
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
            goodsOrder.setState(g.getState());
            submitGoodsList.add(goodsOrder);
            /*goodsnum+=g.getNum();
            goodsprice+=g.getGoods_price()*g.getNum();*/
        }
        model.addAttribute("submitGoodsAllList",submitGoodsList);
        model.addAttribute("active","1");
        return "table/basic_table";
    }
    @GetMapping("dynamic_table")
    public String dynamic_table(Model model){
        model.addAttribute("active","2");
        return "table/dynamic_table";
    }
    @GetMapping("responsive_table")
    public String responsive_table(Model model){
        model.addAttribute("active","3");
        return "table/responsive_table";
    }

}
