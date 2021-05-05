package com.zhangheng.file.controller2;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.zhangheng.file.bean.Goods;
import com.zhangheng.file.bean.Store;
import com.zhangheng.file.repository.GoodsRepository;
import com.zhangheng.file.repository.StoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class FindGoodsController {
    Logger log= LoggerFactory.getLogger(getClass());
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private StoreRepository storeRepository;
    @GetMapping("goodsfromstore_table")
    public String editable_table(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        Store store = (Store) session.getAttribute("store");
        Integer id = store.getStore_id();
        String name = store.getBoss_name();
        String store_namee = store.getStore_name();
        if (id!=null&&store_namee!=null&&name!=null) {
            List<Store> storeList = storeRepository.findByStore_idAndStore_nameAndBoss_name(id, store_namee, name);
            if (storeList.size()>0) {   
                List<Goods> goodsList = goodsRepository.findByStore_id(id);
                model.addAttribute("goodslist",goodsList);
                model.addAttribute("code",200);
            }else {
                model.addAttribute("code",404);
            }
        }else {
            model.addAttribute("code",403);
        }
        model.addAttribute("active","4");
        return "table/editable_table";
    }
    @PostMapping("deleteGoods")//删除商品
    public String deleteGoods(@RequestParam("store_id")Integer store_id
            , @RequestParam("goods_id")Integer goods_id, HttpServletRequest request, Model model){
        String p="files/";
        String msg=null;
        HttpSession session =request.getSession();
        Store store = (Store) session.getAttribute("store");
        Optional<Goods> byId = goodsRepository.findById(goods_id);
        if (byId.isPresent()) {
            File file = new File(p + byId.get().getGoods_image());
            if (file.exists()){
                file.delete();
                log.info("商品图片删除成功");
                goodsRepository.deleteByStore_idAndGoods_id(store_id,goods_id);
                msg="商品删除成功";
            }else {
                log.error("商品图片不存在");
                msg="商品删除失败";
            }
            model.addAttribute("msg",msg);
        }
//        log.info(store_id+"\t"+goods_id);
        return "redirect:goodsfromstore_table";
    }
    @PostMapping("updeteGoods")//修改商品
    public String updeteGoods(@RequestParam("store_id")Integer store_id
            , @RequestParam("goods_id")Integer goods_id, HttpServletRequest request, HttpServletResponse response, Model model){
        HttpSession session =request.getSession();
        Store store = (Store) session.getAttribute("store");
        Optional<Goods> byId = goodsRepository.findById(goods_id);
//        log.info(store_id+"\t"+goods_id);
        if (byId.isPresent()) {
            session.setAttribute("et_goods", byId.get());
        }
        return "redirect:dynamic_table";
    }
}
