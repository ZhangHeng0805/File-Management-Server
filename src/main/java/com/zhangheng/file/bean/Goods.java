package com.zhangheng.file.bean;

import javax.persistence.*;

/*
*
* 商品信息
* */
@Entity
@Table(name = "goods")
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer goods_id;//商品id
    @Column
    private String goods_name;//名称
    @Column
    private String goods_image;//图片地址
    @Column
    private String goods_introduction;//介绍
    @Column
    private String goods_type;//类型
    @Column
    private Integer goods_month_much;//月销量
    @Column
    private double goods_price;//价格
    @Column
    private String store_name;//店名
    @Column
    private Integer store_id;//店铺id
    @Column
    private String time;

    public Integer getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_image() {
        return goods_image;
    }

    public void setGoods_image(String goods_image) {
        this.goods_image = goods_image;
    }

    public String getGoods_introduction() {
        return goods_introduction;
    }

    public void setGoods_introduction(String goods_introduction) {
        this.goods_introduction = goods_introduction;
    }

    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }

    public Integer getGoods_month_much() {
        return goods_month_much;
    }

    public void setGoods_month_much(Integer goods_month_much) {
        this.goods_month_much = goods_month_much;
    }

    public double getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(double goods_price) {
        this.goods_price = goods_price;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public Integer getStore_id() {
        return store_id;
    }

    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
