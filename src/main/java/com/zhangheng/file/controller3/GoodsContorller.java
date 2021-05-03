package com.zhangheng.file.controller3;

import com.google.gson.Gson;
import com.zhangheng.file.bean.Goods;
import com.zhangheng.file.bean.submitgoods.SubmitGoods;
import com.zhangheng.file.bean.submitgoods.goods;
import com.zhangheng.file.repository.GoodsRepository;
import com.zhangheng.file.repository.SubmitGoodsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;

@RequestMapping("Goods")
@RestController
public class GoodsContorller {
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private SubmitGoodsRepository submitGoodsRepository;
    Logger log = LoggerFactory.getLogger(getClass());

    @PostMapping("allgoodslist")
    public List<Goods> getAllList(@Nullable @RequestParam String GoodsType){
        List<Goods> goods = new ArrayList<>();
        log.info("查询商品类型："+GoodsType);
        if (GoodsType!=null) {
            if (GoodsType.equals("全部")) {
                goods = goodsRepository.findAll();
            } else {
                goods = goodsRepository.findByGoods_type(GoodsType);
            }
        }
//        List<Goods> goods = new ArrayList<>();

        return goods;
    }
    @PostMapping("submitgoodslist")
    public String submit(@RequestParam String submitGoodsList){
        String msg="";
        log.info(submitGoodsList);
        Gson gson = new Gson();
        if (submitGoodsList.length()>5) {
            SubmitGoods submitGoods = gson.fromJson(submitGoodsList, SubmitGoods.class);
            List<goods> goods_list = submitGoods.getGoods_list();
            List<Integer> id=new ArrayList<>();
            List<Integer> n=new ArrayList<>();
            for (goods g:goods_list){
                id.add(g.getGoods_id());
                n.add(g.getNum());
                g.setState("未处理");
            }
            List<Goods> allById = goodsRepository.findAllById(id);
            double count = 0;
            for (Goods goods:allById) {
                for (goods g:goods_list){
                    if (g.getGoods_id().equals(goods.getGoods_id())){
                        count+=goods.getGoods_price()*g.getNum();
                    }
                }
            }
            BigDecimal bg = new BigDecimal(count);
            count= bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            log.info("总金额："+count);
            if (count==submitGoods.getCount_price()){
//                for (goods g:goods_list) {
//                    goodsRepository.addGood_month_much(g.getNum(),g.getGoods_id());
//                }
                SubmitGoods submitGoods1 = submitGoodsRepository.saveAndFlush(submitGoods);
                if (submitGoods1!=null) {
                    msg = "成功";
                }else {
                    msg = "失败";
                }

            }else {
                msg="失败";
            }
        }
        return msg;
    }
}
