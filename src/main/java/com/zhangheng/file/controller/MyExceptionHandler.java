package com.zhangheng.file.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler({Exception.class})
    public String handleException(Exception e, HttpServletRequest request,Map<String,Object> map){
//        request.setAttribute("javax.servlet.error.status_code",code);
        map.put("message",e.getMessage());
        return "forward:/error";
    }
}
