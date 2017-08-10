package com.jt.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ReportAsSingleViolation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.util.CookieUtils;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;
import com.jt.web.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	public static final String JT_TICKET="JT_TICKET";
	
	/**
	 * 转向注册页面
	 * @return
	 */
	@RequestMapping("/register.html")
	public String register(){
		return "register";
	}
	//转向登录页面
	@RequestMapping("/login.html")
	public String login(){
		return "login";
	}
	//注册
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult doRegister(User user) throws Exception{
		return userService.register(user);
	}
	//登录	
		@RequestMapping("/doLogin")
		@ResponseBody
		public SysResult doLogin(User user,HttpServletResponse response,HttpServletRequest request) throws Exception{
			String ticket= userService.login(user);
			CookieUtils.setCookie(request, response, JT_TICKET, ticket);			
			return SysResult.oK(ticket);
		}
		
		//登录	
	@RequestMapping("/logout")
	@ResponseBody
	public String doLogout(User user,HttpServletResponse response,HttpServletRequest request) throws Exception{
		CookieUtils.deleteCookie(request, response, JT_TICKET);					
		return "index";
	}
}
