package com.zhangheng.file.controller2;

import com.zhangheng.file.bean.Goods;
import com.zhangheng.file.repository.GoodsRepository;
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

import java.io.File;
import java.util.List;
import java.util.UUID;

@Controller
public class AddGoodsController {
    @Autowired
    private GoodsRepository goodsRepository;
    Logger log = LoggerFactory.getLogger(getClass());
    private static String baseDir = "files/goods/";

    @PostMapping("responsive_table")
    public String responsive_table(MultipartFile image, Goods goods, Model model){
        if (!image.isEmpty()){
            String fileType = FiletypeUtil.getFileType(image.getOriginalFilename());
            if (fileType.equals("image")) {
                if (image.getSize() < 1040000) {
                    if (goods.getGoods_name().length() > 0&&goods.getGoods_introduction().length()>0) {
                        List<Goods> goodsList = goodsRepository.findByGoods_nameAndGoods_introduction(goods.getGoods_name(), goods.getGoods_introduction());
                        if (goodsList.size()==0){
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
                                flag = true;
                                goods.setGoods_image("goods/"+name);
                            } catch (Exception e) {
                                log.error(e.getMessage());
                            }
                            if (flag){
                                goods.setGoods_month_much(0);
                                Goods save = goodsRepository.save(goods);
                                model.addAttribute("code",200);
                                model.addAttribute("msg","商品:<"+save.getGoods_name()+">添加成功！");
                            }else {
                                model.addAttribute("msg", "图片上传失败");
                            }
                        }else {
                            model.addAttribute("msg", "商品已存在，请勿重复添加");
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
            model.addAttribute("msg","上传的图片为空");
        }

        model.addAttribute("active","3");
        return "table/responsive_table";
    }
}
