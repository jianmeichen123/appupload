package com.galaxy.appupload.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.galaxy.appupload.service.LoginService;
import com.galaxy.appupload.util.Base64Coding;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	LoginService loginService;
	
	//登录功能
	@RequestMapping("/userLogin")
	public String userLogin(HttpServletRequest request,HttpServletResponse response){
		Boolean result = false;
		String msg="用户名或密码错误";
		//获取用户名和密码
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if(!("".equals(username))&&username!=null&&password!=null&&!("".equals(password))){
			result = loginService.getLogin(username,Base64Coding.encode(password));
		}else{
			//用户名或密码为空,说明是被拦截进来的,拦截的controller层不需要提示语,所以置为空(页面不提示用户名密码错误);
			msg="";
		}
		if(result){
			//将用户保存到session中
			request.getSession().setAttribute("username", username);
			return "redirect:/appManager/addApplication.action";
		}else{
			request.setAttribute("username", username);
			request.setAttribute("msg", msg);
			return "/index";
		}
	}

}
