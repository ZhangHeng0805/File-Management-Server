package com.zhangheng.file;

import com.zhangheng.file.bean.Goods;
import com.zhangheng.file.bean.submitgoods.SubmitGoods;
import com.zhangheng.file.bean.submitgoods.goods;
import com.zhangheng.file.entity.PhoneMessage;
import com.zhangheng.file.entity.S3Config;
import com.zhangheng.file.entity.User;
import com.zhangheng.file.repository.GoodsRepository;
import com.zhangheng.file.repository.ListGoodsRepository;
import com.zhangheng.file.repository.PhoneMessageRepository;
import com.zhangheng.file.repository.SubmitGoodsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class DemoUploadApplicationTests {

    @Autowired
    User user;
    @Autowired
    S3Config s3Config;
    @Test
    void contextLoads() {
        System.out.println(user);
        System.out.println(s3Config);
        System.out.println("_____________");
        try {
//            ArrayList<Object> files = FolderFileScanner.scanFilesWithNoRecursion("files");
//            ArrayList<Object> files1 = FolderFileScanner.scanFilesWithRecursion("files");
//            System.out.println(files);
            System.out.println("_____________");
//            System.out.println(files1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*    @Autowired
    PhoneMessageRepository phoneMessageRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Test
    public void test1(){
        PhoneMessage phoneMessage=new PhoneMessage();
        phoneMessage.setPhonenum("1qq");
        phoneMessage.setModel("111");
        phoneMessage.setSdk("123");
        phoneMessage.setRelease("123");
        phoneMessage.setTime("123");
//        phoneMessageRepository.saveAndFlush(phoneMessage);
//        List<PhoneMessage> all = phoneMessageRepository.findAll();
//        System.out.println(all);

    }*/
    /*@Test
    public void test2(){
        String path="files/image/星曦向荣网76eea_64b6c_软件下载二维码.png";
        File file=new File(path);
        if (file.exists()){
            file.delete();
            System.out.println("文件删除成功");
        }else {
            System.out.println("文件不存在");
        }
    }*/
    @Autowired
    GoodsRepository goodsRepository;
    @Test
    public void test3(){
        /*List<Goods> goodsList = goodsRepository.findByGoods_nameAndGoods_introduction("0", "1");
        if (goodsList.size()>0) {
            System.out.println(goodsList.get(0).getGoods_image());
        }
        System.out.println(goodsList.size());*/
//        goodsRepository.addGood_month_much(2);
    }
    @Autowired
    private SubmitGoodsRepository submitGoodsRepository;
    private ListGoodsRepository listGoodsRepository;
    @Test
    public void test4(){
/*        goods goods0 = new goods();
        goods goods1 = new goods();

        goods0.setGoods_id(1);
        goods0.setGoods_name("1");
        goods0.setGoods_price(1.0);
        goods0.setNum(2);

        goods1.setGoods_id(2);
        goods1.setGoods_name("1");
        goods1.setGoods_price(1.5);
        goods1.setNum(2);


        List<goods> list=new ArrayList<>();
        list.add(goods0);
        list.add(goods1);
        SubmitGoods submitGoods = new SubmitGoods();
        submitGoods.setAddress("武汉");
        submitGoods.setCount_price(5.0);
        submitGoods.setGoods_list(list);
        submitGoods.setName("zh");
        submitGoods.setPhone("120");
        submitGoods.setSubmit_id("1.1.1");
        List<SubmitGoods> all = submitGoodsRepository.findAll();
        for (SubmitGoods s:all) {
            System.out.println(s);
        }*/
    }

}
