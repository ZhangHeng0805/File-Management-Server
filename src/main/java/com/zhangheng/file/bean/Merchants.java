package com.zhangheng.file.bean;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Component
@Table(name = "merchants")
@Entity
public class Merchants {
    @Id
    private String phonenum;//电话号码
    @Column
    private String name;//姓名
    @Column
    private String email;//邮箱
    @Column
    private String account;//用户名
    @Column
    private String password;//密码
    @Column
    private String address;//地址
    @Column
    private String gender;//性别
    @Column
    private String store_name;//商铺名称
    @Column
    private String store_introduce;//商铺名称
    @Column
    private Integer store_id;
    @Column
    private String time;//注册时间

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_introduce() {
        return store_introduce;
    }

    public void setStore_introduce(String store_introduce) {
        this.store_introduce = store_introduce;
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

    @Override
    public String toString() {
        return "Merchants{" +
                "phonenum='" + phonenum + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", gender='" + gender + '\'' +
                ", store_name='" + store_name + '\'' +
                ", store_introduce='" + store_introduce + '\'' +
                ", store_id=" + store_id +
                ", time='" + time + '\'' +
                '}';
    }
}
