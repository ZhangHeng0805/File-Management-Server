package com.zhangheng.file.repository;

import com.zhangheng.file.bean.Goods;
import com.zhangheng.file.bean.submitgoods.SubmitGoods;
import com.zhangheng.file.bean.submitgoods.goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface SubmitGoodsRepository extends JpaRepository<SubmitGoods, String> {
    //根据订单号查询订单
    @Query("select u from SubmitGoods u where u.submit_id = ?1")
    SubmitGoods findBySubmit_id(String submit_id);
    //根据手机号查询订单
    @Query("select u from SubmitGoods u where u.phone = ?1")
    List<SubmitGoods> findAllByPhone(String phone);
}
