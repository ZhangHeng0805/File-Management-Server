package com.zhangheng.file.controller;

import com.zhangheng.file.entity.Delete_Image;
import com.zhangheng.file.bean.Goods;
import com.zhangheng.file.bean.GoodsOrder;
import com.zhangheng.file.bean.Store;
import com.zhangheng.file.bean.submitgoods.SubmitGoods;
import com.zhangheng.file.bean.submitgoods.goods;
import com.zhangheng.file.repository.DeleteImageRepository;
import com.zhangheng.file.repository.GoodsRepository;
import com.zhangheng.file.repository.ListGoodsRepository;
import com.zhangheng.file.repository.SubmitGoodsRepository;
import com.zhangheng.file.util.FiletypeUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class TableController {
    @Autowired
    private SubmitGoodsRepository submitGoodsRepository;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private ListGoodsRepository listGoodsRepository;
    @Autowired
    private DeleteImageRepository deleteImageRepository;
    private static String baseDir = "files/goods/";
    Logger log = LoggerFactory.getLogger(getClass());


    @GetMapping("basic_table")//跳转至订单列表
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
    @PostMapping("dynamic_table")
    public String updataGoods(MultipartFile image, Goods goods, Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        Goods et_goods = (Goods) session.getAttribute("et_goods");
        String p="files/";
        if (!image.isEmpty()){
            String fileType = FiletypeUtil.getFileType(image.getOriginalFilename());
            if (fileType.equals("image")) {
                if (image.getSize() < 1040000) {
                    if (goods.getGoods_name().length() > 0&&goods.getGoods_introduction().length()>0) {

                            boolean flag = false;
                            String filename = image.getOriginalFilename();
                            log.info("商品图片名：{}", filename);
                            log.info("商品图片大小：{}字节", image.getSize());
                            filename = filename
                                    .replace(" ", "")
                                    .replace("星曦向荣网", "")
                                    .replace("星曦向荣", "")
                            ;
                            String name =
                                    "商品图"
                                            + UUID.randomUUID().toString().substring(0, 8)
                                            + "_" + goods.getGoods_name()+filename.substring(filename.lastIndexOf("."));
                            File outFile = new File(baseDir + name);
                            try {
                                FileUtils.copyToFile(image.getInputStream(), outFile);
                                File file = new File(p + et_goods.getGoods_image());
                                if (file.exists()) {
                                    boolean delete = file.delete();
                                    if (delete){
                                        log.info("商品图片删除成功");
                                    }else {
                                        Delete_Image delete_image = new Delete_Image();
                                        delete_image.setImage_url(p + et_goods.getGoods_image());
                                        Delete_Image delete_image1 = deleteImageRepository.saveAndFlush(delete_image);
                                    }
                                }else {
                                    log.error("商品图片不存在");
                                }
                                flag = true;
                                goods.setGoods_image("goods/"+name);
                            } catch (Exception e) {
                                log.error(e.getMessage());
                            }
                            if (flag){
                                goods.setGoods_month_much(et_goods.getGoods_month_much());
                                Goods save = goodsRepository.saveAndFlush(goods);
                                model.addAttribute("code",200);
                                model.addAttribute("msg","商品:<"+save.getGoods_name()+">修改成功！");
                                session.setAttribute("et_goods",save);
                            }else {
                                model.addAttribute("msg", "图片上传失败");
                            }

                    } else {
                        model.addAttribute("msg", "添加信息为空，请重试");
                    }
                } else {
                    model.addAttribute("msg", "图片文件过大，大小限制1Mb以内");
                }
            }else {
                model.addAttribute("msg", "上传的图片文件格式错误");
            }
        }else {
            goods.setGoods_month_much(et_goods.getGoods_month_much());
            goods.setGoods_image(et_goods.getGoods_image());
            Goods goods1 = goodsRepository.saveAndFlush(goods);
            model.addAttribute("code",200);
            model.addAttribute("msg","商品:<"+goods1.getGoods_name()+">修改成功！");
            session.setAttribute("et_goods",goods1);
        }
        model.addAttribute("active","2");
        return "table/dynamic_table";
    }
    @GetMapping("responsive_table")
    public String responsive_table(Model model){
        model.addAttribute("active","3");
        return "table/responsive_table";
    }
    @GetMapping("modify_account")
    public String modify_account(Model model){
        model.addAttribute("active","0");
        return "table/modify_account";
    }

}
