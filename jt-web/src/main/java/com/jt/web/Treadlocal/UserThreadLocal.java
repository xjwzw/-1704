package com.jt.web.Treadlocal;

import com.jt.web.pojo.User;

public class UserThreadLocal {
	private static final ThreadLocal<User> User=new ThreadLocal<User>();
	
	public static User get(){
		return User.get();
	}
	
	public static void set(User user){
		User.set(user);
	}
	
	public static Long getUserId(){
		try {
			return User.get().getId();
		} catch (Exception e) {
			return null;   //调用者通过null判断业务走向
		}
	}
}
