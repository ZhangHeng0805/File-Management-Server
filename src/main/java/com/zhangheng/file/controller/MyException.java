package com.zhangheng.file.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MyException implements ErrorController {

    Logger log=LoggerFactory.getLogger(getClass());

    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        Integer statusCode;
        statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        switch (statusCode) {
            case 404:
                log.info("404异常跳转");
                return "/error/404";
            case 403:
                log.info("403异常跳转");
                return "/error/403";
            case 405:
                log.info("403异常跳转");
                return "/error/405";
            case 500:
                log.info("500异常跳转");
                return "/error/500";
            default:
                log.info("默认异常跳转");
                return "/error/404";
        }

    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
