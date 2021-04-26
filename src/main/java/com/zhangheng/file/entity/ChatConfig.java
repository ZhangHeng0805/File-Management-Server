package com.zhangheng.file.entity;

import javax.persistence.*;

@Entity
@Table(name = "chatconfig")
public class ChatConfig {
    @Id //这是一个主键
    private String id;
    @Column
    private String ip;
    @Column(name = "[port]")
    private String port;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "ChatConfig{" +
                "id='" + id + '\'' +
                ", ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                '}';
    }
}
