package com.jt.sso.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.BaseService;
import com.jt.common.service.RedisService;
import com.jt.sso.mapper.UserMapper;
import com.jt.sso.pojo.User;

@Service
public class UserService extends BaseService<User>{
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RedisService redisService;
	private static final ObjectMapper mapper=new ObjectMapper();
	public Boolean check(String param, Integer type) {
		Map<String,Object> map=new HashMap<String,Object>();
	   if(1==type){
		   map.put("col", "username");
	   }if(2==type){
		   map.put("col", "phone");		   
	   }if(3==type){
		   map.put("col", "email");
	   }
	   	map.put("val", param);
		Integer count =userMapper.check(map);
		if(count>0){
		return true;
		}else{
			return false;
		}
	}
	//用户注册
	public String register(User user){
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		if(StringUtils.isEmpty(user.getEmail())){
			//js加判断，如果email是以---符号开头，不展示
			user.setEmail("---"+user.getPhone());	
		}
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		super.saveSelective(user);
		return user.getUsername();
	}
	//用户登录
	public String login(String username, String password) {
		User user=new User();
		user.setUsername(username);
		User curUser=super.queryByWhere(user);
		password=DigestUtils.md5Hex(password);
		if(password.equals(curUser.getPassword())){
			try {
			String ticket=DigestUtils.md5Hex("TICKET_"+username+curUser.getId()
			+System.currentTimeMillis());
				String userJason=mapper.writeValueAsString(curUser);
				//写redis
				redisService.set(ticket,userJason,60*60*24*7);
				return ticket;
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}		
		return null;
	}
}
