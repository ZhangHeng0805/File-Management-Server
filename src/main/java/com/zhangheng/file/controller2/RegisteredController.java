package com.zhangheng.file.controller2;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.zhangheng.file.bean.Merchants;
import com.zhangheng.file.bean.Store;
import com.zhangheng.file.repository.MerchantsRepository;
import com.zhangheng.file.repository.StoreRepository;
import com.zhangheng.file.util.FiletypeUtil;
import com.zhangheng.file.util.PhoneNumUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import java.util.UUID;

@Controller
public class RegisteredController {
    @Autowired
    private MerchantsRepository merchantsRepository;
    @Autowired
    private StoreRepository storeRepository;
    private static String baseDir = "files/stores/";;
    Logger log = LoggerFactory.getLogger(getClass());

    @PostMapping("/registration_html")
    public String registeredMerchants(MultipartFile store_image,Merchants merchants, Model model){
        Store store=new Store();
        if (!store_image.isEmpty()) {
            String fileType = FiletypeUtil.getFileType(store_image.getOriginalFilename());
            if (fileType.equals("image")) {
                if (store_image.getSize() < 1040000) {
                    if (merchants.getPhonenum().length() > 1) {
                        if (PhoneNumUtil.isMobile(merchants.getPhonenum())) {
                            boolean b = merchantsRepository.existsById(merchants.getPhonenum());
                            if (!b) {
                                boolean flag = false;
                                String filename = store_image.getOriginalFilename();
                                log.info("店铺图片名：{}", filename);
                                log.info("店铺图片大小：{}字节", store_image.getSize());
                                filename = filename
                                        .replace(" ", "")
                                        .replace("星曦向荣网", "")
                                        .replace("星曦向荣", "")
                                ;
                                String name =
                                        "商铺图"
                                        + UUID.randomUUID().toString().substring(0, 8)
                                        + "_" + merchants.getStore_name();
                                File outFile = new File(baseDir + name);
                                try {
                                    FileUtils.copyToFile(store_image.getInputStream(), outFile);
                                    flag = true;
                                    store.setStore_image("stores/"+name);
                                } catch (Exception e) {
                                    log.error(e.getMessage());
                                }
                                if (flag) {
                                    store.setBoss_name(merchants.getName());
                                    store.setStart_time(merchants.getTime());
                                    store.setStore_introduce(merchants.getStore_introduce());
                                    store.setStore_name(merchants.getStore_name());
                                    Store save1 = storeRepository.save(store);
                                    Integer store_id = save1.getStore_id();
                                    merchants.setStore_id(store_id);
                                    Merchants save = merchantsRepository.save(merchants);
                                    model.addAttribute("msg", "恭喜 " + save.getAccount() + " 注册成功");
                                    model.addAttribute("code", 200);
                                } else {
                                    model.addAttribute("msg", "图片上传失败");
                                }
                            }else {
                                model.addAttribute("msg", "电话号码已经注册，请勿重复注册");
                            }
                        } else {
                            model.addAttribute("msg", "电话号码格式错误，请使用正确的号码");
                        }
                    } else {
                        model.addAttribute("msg", "注册号码为空，请重试");
                    }
                } else {
                    model.addAttribute("msg", "图片文件过大，大小限制1Mb以内");
                }
            } else {
                model.addAttribute("msg", "上传的图片文件格式错误");
            }
        }else {
        model.addAttribute("msg", "图片为空");
    }
        return "registration";
    }


}
