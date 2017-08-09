package com.jt.web.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.BaseService;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;

@Service
public class UserService {
	@Autowired
	private HttpClientService httpClientService;
	private static final ObjectMapper mapper=new ObjectMapper();
	public SysResult register(User user) throws Exception{
		//利用httpclient请求
		String url="http://sso.jt.com/user/register";
		Map<String,String>  params=new HashMap<String,String>();
		params.put("username",user.getUsername());
		params.put("password",user.getPassword());
		params.put("phone",user.getPhone());
		params.put("email",user.getEmail());
		String jasonData=httpClientService.doPost(url, params);
		//把数据取出来，然后放进去 sysresult这个对象写的不标准，Jackson不能正确转换
		JsonNode jsonNode=mapper.readTree(jasonData);
		 String username=jsonNode.get("data").asText();
		 return SysResult.oK(username);
	}
	
	public String login(User user) throws Exception {
		String url="http://sso.jt.com/user/login";
		Map<String,String> params=new HashMap<String,String>();
		params.put("u", user.getUsername());
		params.put("p", user.getPassword());
		
		String jasonData=httpClientService.doPost(url, params);
		JsonNode jsonNode=mapper.readTree(jasonData);
		String ticket=jsonNode.get("data").asText();		
		return ticket;
	}
}
