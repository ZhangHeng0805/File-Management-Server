package com.zhangheng.file.entity;


import javax.persistence.*;

@Entity
@Table(name = "delete_image")
public class Delete_Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer image_id;//商品id
    @Column
    private String image_url;//名称

    public Integer getImage_id() {
        return image_id;
    }

    public void setImage_id(Integer image_id) {
        this.image_id = image_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
