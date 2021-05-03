package com.zhangheng.file.interceptor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
* 登录检查
* 1.配置好拦截器要拦截哪些请求
* 2.把这些配置放在容器中
* */
public class LoginInterceptor implements HandlerInterceptor {

    Logger log=LoggerFactory.getLogger(getClass());
    /*
    * 目标方法执行之前
    * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //登录检查
        HttpSession session =request.getSession();
//        String requestURI = request.getRequestURI();
//        log.info("拦截的请求路径是{}",requestURI);
        Object loginUser = session.getAttribute("loginUser");
        Object store = session.getAttribute("store");
        if (loginUser!=null&&store!=null){
            //放行
            return true;
        }
        request.setAttribute("msg","请先登录");
        request.getRequestDispatcher("/").forward(request,response);
        //拦截
        return false;
    }

    /*
    * 目标方法完成之后
    * */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /*
    * 页面渲染完成之后
    * */

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
