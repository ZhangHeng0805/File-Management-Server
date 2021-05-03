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
public interface ListGoodsRepository extends JpaRepository<goods, Integer> {

    //根据店铺id查询订单
    @Query("select u from goods u where u.store_id = ?1")
    List<goods> findByStore_id(Integer store_id);
    //根据店铺id和订单状态查询订单
    @Query("select u from goods u where u.store_id = ?1 and u.state=?2")
    List<goods> findByStore_idAndState(Integer store_id,String state);

    @Modifying
    @Query("update goods sc set sc.state =  ?1 where sc.list_id = ?2 and sc.goods_id=?3")
    public void updateStateByList_idAndGoods_id(String state, String list_id,Integer goods_id);
}
