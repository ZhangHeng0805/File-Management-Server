package com.zhangheng.file.repository;

import com.zhangheng.file.bean.Goods;
import com.zhangheng.file.bean.Merchants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface GoodsRepository extends JpaRepository<Goods, Integer> {

    //根据商品名和介绍查询商品
    @Query("select u from Goods u where u.goods_name = ?1 and u.goods_introduction = ?2")
    List<Goods> findByGoods_nameAndGoods_introduction(String goods_name,String goods_introduction);

    //修改商品销量
    @Modifying
    @Query("update Goods sc set sc.goods_month_much = sc.goods_month_much + ?1 where sc.goods_id = ?2")
    public void addGood_month_much(Integer num, Integer goods_id);

    //根据店铺id查询商品
    @Query("select u from Goods u where u.store_id = ?1")
    List<Goods> findByStore_id(Integer store_id);

    //根据商品类型查询商品
    @Query("select u from Goods u where u.goods_type = ?1")
    List<Goods> findByGoods_type(String type);
}
