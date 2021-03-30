package com.zhangheng.file.entity;

import org.springframework.stereotype.Component;

@Component
public class Result {
    private String title;
    private String message;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Result{" +
                "title='" + title + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
