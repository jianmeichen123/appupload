package com.galaxy.appupload.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class CommonInterceptor extends HandlerInterceptorAdapter{
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
    	
    	//指定页面不缓存
        response.setDateHeader("Expires", -1);//IE
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
    	
        //登录，版本检测，二维码下载action
    	String loginUrl="/login/userLogin";
    	String checkUrl="/appManager/checkVersion";
    	String qrCodeUrl="/appManager/qrCodeDownload";
    	String qrCode="/appManager/getQRcode";
    	String qrCodeCreate="/appManager/createQRcode";
    	String showImage="/appManager/showImage";
    	String download="/appManager/download";
    	//1、请求到登录页面 放行,不拦截登录action
        if(request.getServletPath().startsWith(loginUrl)||request.getServletPath().startsWith(checkUrl)
        		||request.getServletPath().startsWith(qrCodeUrl)||request.getServletPath().startsWith(qrCode)
        		||request.getServletPath().startsWith(qrCodeCreate)||request.getServletPath().startsWith(showImage)
        		||request.getServletPath().startsWith(download)) {  
            return true;  
        }  
        //2、如果用户已经登录 放行    
        if(request.getSession().getAttribute("username") != null) {  
            //更好的实现方式的使用cookie  
            return true;  
        }  
              
        //4、非法请求 即这些请求需要登录后才能访问  
        //重定向到登录页面  
        response.sendRedirect(request.getContextPath() + loginUrl);  
        return false; 
    }
    
    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }
}