package com.zhangheng.file.entity;



import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table
@Component
public class PhoneMessage {

    @Id
    private String phonenum;
    @Column(name = "[model]")
    private String model;
    @Column(name = "[sdk]")
    private String sdk;
    @Column(name = "[release]")
    private String release;
    @Column(name = "[time]")
    private String time;

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSdk() {
        return sdk;
    }

    public void setSdk(String sdk) {
        this.sdk = sdk;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "PhoneMessage{" +
                "phonenum='" + phonenum + '\'' +
                ", model='" + model + '\'' +
                ", sdk='" + sdk + '\'' +
                ", release='" + release + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
