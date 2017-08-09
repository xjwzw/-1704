package com.jt.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.cart.pojo.Cart;
import com.jt.cart.service.CartService;
import com.jt.common.vo.SysResult;

@Controller
public class cartController {
	@Autowired
	private CartService cartService;
	@RequestMapping("/cart/query/{userId}")
	@ResponseBody
	public SysResult queryByUserId(@PathVariable Long userId){
			List<Cart> cartList=cartService.queryByUserId(userId);
			return SysResult.oK(cartList);
		}
	@RequestMapping("/cart/save")
	@ResponseBody
	public SysResult saveCart(Cart cart){
		 cartService.saveCart(cart);
		 return SysResult.oK();
	}
	
}
