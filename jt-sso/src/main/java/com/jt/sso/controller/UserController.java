package com.jt.sso.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.service.RedisService;
import com.jt.common.vo.SysResult;
import com.jt.sso.pojo.User;
import com.jt.sso.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private RedisService redisService;
	private static final Logger log=Logger.getLogger(UserController.class);
	
	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public SysResult check(String callback,@PathVariable String param, @PathVariable Integer type){		
		try {
			Boolean b= userService.check(param,type);	
			return SysResult.oK(b);
		} catch (Exception e) {
			log.error(e.getMessage());
			return SysResult.build(201, e.getMessage());
		}		
	}
	@RequestMapping("/register")
	@ResponseBody
	public SysResult regist(User user){
		try {
			String username=userService.register(user);
			return SysResult.oK(username);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return SysResult.build(201, e.getMessage());
		}
		
	}
	@RequestMapping("/login")
	@ResponseBody
	public SysResult login(String u ,String p){
		String ticket=userService.login(u,p);
		return SysResult.oK(ticket);
	}
	
	@RequestMapping("/query/{ticket}")
	@ResponseBody
	public SysResult findUserByTicket(@PathVariable String ticket){
		try {
			String userJason=redisService.get(ticket);
			return SysResult.oK(userJason);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, e.getMessage());
		}		
	}
	
}
