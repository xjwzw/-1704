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
@RequestMapping("/cart")
public class cartController {
	@Autowired
	private CartService cartService;
	@RequestMapping("/query/{userId}")
	@ResponseBody
	public SysResult queryByUserId(@PathVariable Long userId){
			List<Cart> cartList=cartService.queryByUserId(userId);
			return SysResult.oK(cartList);
		}
	//新增购物车
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveCart(Cart cart){
		System.out.println(111);
		 cartService.saveCart(cart);
		 return SysResult.oK();
	}
	//更新购物车
	@RequestMapping("/update/num/{userId}/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCart(Cart cart){		
		 cartService.updateCart(cart);
		 return SysResult.oK();
	}
	//删除购物车商品
	@RequestMapping("/delete/{userId}/{itemId}")
	@ResponseBody
	public SysResult deleteCart(Cart cart){
		 cartService.deleteCart(cart);
		 return SysResult.oK();
	}
}
