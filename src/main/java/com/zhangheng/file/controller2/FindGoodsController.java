package com.zhangheng.file.controller2;

import com.zhangheng.file.bean.Goods;
import com.zhangheng.file.bean.Store;
import com.zhangheng.file.repository.GoodsRepository;
import com.zhangheng.file.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FindGoodsController {
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private StoreRepository storeRepository;
    @GetMapping("goodsfromstore_table")
    public String editable_table(@Nullable @RequestParam("id")Integer id,@Nullable @RequestParam("store_name")String store ,@Nullable @RequestParam("name")String name, Model model){
        if (id!=null&&store!=null&&name!=null) {
            List<Store> storeList = storeRepository.findByStore_idAndStore_nameAndBoss_name(id, store, name);
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
}
