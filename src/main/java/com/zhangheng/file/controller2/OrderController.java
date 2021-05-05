package com.zhangheng.file.controller2;

import com.zhangheng.file.bean.submitgoods.SubmitGoods;
import com.zhangheng.file.entity.Result;
import com.zhangheng.file.repository.GoodsRepository;
import com.zhangheng.file.repository.ListGoodsRepository;
import com.zhangheng.file.repository.SubmitGoodsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {
    Logger log= LoggerFactory.getLogger(getClass());
    @Autowired
    private ListGoodsRepository listGoodsRepository;
    @Autowired
    private SubmitGoodsRepository submitGoodsRepository;
    @Autowired
    private GoodsRepository goodsRepository;

    @PostMapping("Insert_Order")//根据手机号查询订单
    @ResponseBody
    public List<SubmitGoods> submitGoodsList(@RequestParam("phone") String phone){
        List<SubmitGoods> list=new ArrayList<>();
        if (phone.length()==11){
            list=submitGoodsRepository.findAllByPhone(phone);
        }
        return list;
    }

    @PostMapping("OK_Order")//订单商品确认收货
    @ResponseBody
    public Result submitOrderOK(
            @RequestParam("num") int num
            ,@RequestParam("submit_id") String submit_id
            ,@RequestParam("phone") String phone
            ,@RequestParam("goods_id") Integer goods_id){
        Result result = new Result();
        if (phone.length() == 11) {
            try {
                listGoodsRepository.updateStateByList_idAndGoods_id("已收货",submit_id,goods_id);
                goodsRepository.addGood_month_much(num,goods_id);
                result.setTitle("收货成功");
                result.setMessage(String.valueOf(goods_id));
            }catch (Exception e){
                result.setTitle("错误");
                result.setMessage(e.getMessage());
            }
        }else {
            result.setTitle("信息错误");
            result.setMessage("账户手机号错误");
        }
        return result;
    }
    @PostMapping("NO_Order")//订单商品退货
    @ResponseBody
    public Result submitOrderNO(
            @RequestParam("submit_id") String submit_id
            ,@RequestParam("phone") String phone
            ,@RequestParam("goods_id") Integer goods_id){
        Result result = new Result();
        if (phone.length() == 11) {
            try {
                listGoodsRepository.updateStateByList_idAndGoods_id("退货",submit_id,goods_id);
                result.setTitle("退货成功");
                result.setMessage(String.valueOf(goods_id));
            }catch (Exception e){
                result.setTitle("错误");
                result.setMessage(e.getMessage());
            }
        }else {
            result.setTitle("信息错误");
            result.setMessage("账户手机号错误");
        }
        return result;
    }
    @PostMapping("/Order_Refused/{submit_id}/{goods_id}")//订单拒绝
    public String orderRefused(@PathVariable("submit_id") String submit_id,@PathVariable("goods_id") Integer goods_id){
//        log.info(submit_id+"\t"+goods_id);
        listGoodsRepository.updateStateByList_idAndGoods_id("订单拒绝",submit_id,goods_id);
        return "redirect:/main.html";
    }
    @PostMapping("/Order_Confirm/{submit_id}/{goods_id}")//订单确认
    public String orderConfirm(@PathVariable("submit_id") String submit_id,@PathVariable("goods_id") Integer goods_id){
//        log.info(submit_id+"\t"+goods_id);
        listGoodsRepository.updateStateByList_idAndGoods_id("订单确认",submit_id,goods_id);
        return "redirect:/main.html";
    }
    @PostMapping("Order_Received")//订单收货
    public String orderReceived(@RequestParam("submit_id") String submit_id, @RequestParam("goods_id") Integer goods_id){
//        log.info(submit_id+"\t"+goods_id);
        listGoodsRepository.updateStateByList_idAndGoods_id("已收货",submit_id,goods_id);
        return "redirect:/main.html";
    }
    @PostMapping("Order_Return")//订单退货
    public String orderReturn(@RequestParam("submit_id") String submit_id, @RequestParam("goods_id") Integer goods_id){
//        log.info(submit_id+"\t"+goods_id);
        listGoodsRepository.updateStateByList_idAndGoods_id("已退货",submit_id,goods_id);
        return "redirect:/main.html";
    }
}
