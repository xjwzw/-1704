package com.jt.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.util.CookieUtils;
import com.jt.web.Treadlocal.UserThreadLocal;
import com.jt.web.controller.UserController;
import com.jt.web.pojo.User;

public class CartInterceptor implements HandlerInterceptor{
	@Autowired
	HttpClientService httpClintService;
	
	private static final ObjectMapper mapper=new ObjectMapper();
	//处理controller方法之前调用
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//获取userId
		/**步骤
		 * 1.读取cookie
		 * 2.调用sso业务接口去获取redis中的值
		 * 3.userThreadLocal.user
		 * 4.判断cookie，redis直接跳转登录页面
		 * 
		 */
		String ticket=CookieUtils.getCookieValue(request, UserController.JT_TICKET);
		
		if(StringUtils.isNotEmpty(ticket)){
			String url="http://sso.jt.com/user/query/"+ticket;
			String jasonData=httpClintService.doGet(url);
				if(StringUtils.isNotEmpty(jasonData)){
					JsonNode jsonNode=mapper.readTree(jasonData);
					String userJson=jsonNode.get("data").asText();
					User user=mapper.readValue(userJson, User.class);
					UserThreadLocal.set(user);
					return true;
				}else{
					UserThreadLocal.set(null);
				}
						
		}
		//cookie，redis不存在 重定向登录页面
		response.sendRedirect("/user/login.html");
		
		return false; //false 不放行 true 放行
	}
	//处理controller方法之后调用
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}
	//渲染renderViewResolve之后执行
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
