package com.jt.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.support.RequestPartServletServerHttpRequest;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.util.CookieUtils;
import com.jt.web.Treadlocal.UserThreadLocal;
import com.jt.web.controller.UserController;
import com.jt.web.pojo.User;

public class OrderInterceptor implements HandlerInterceptor{
	@Autowired
	private HttpClientService httpClientService;
	
	private static final ObjectMapper mapper=new ObjectMapper();
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String ticket=CookieUtils.getCookieValue(request, UserController.JT_TICKET);
		if(StringUtils.isNotEmpty(ticket)){
			String url="http://sso.jt.com/query"+ticket;
			String jsonData=httpClientService.doGet(url);
			if(StringUtils.isNotEmpty(jsonData)){
			JsonNode jsonNode=mapper.readTree(jsonData);
			String userJson=jsonNode.get("data").asText();
			User curUser=mapper.readValue(userJson, User.class);	
			UserThreadLocal.set(curUser);
			return true;
			}else{
				UserThreadLocal.set(null);
			}
		}else{
			UserThreadLocal.set(null);
		}		
		response.sendRedirect("/user/login.html");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
