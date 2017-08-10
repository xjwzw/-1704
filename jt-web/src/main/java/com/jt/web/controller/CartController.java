package com.jt.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.web.Treadlocal.UserThreadLocal;
import com.jt.web.pojo.Cart;
import com.jt.web.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private CartService cartService;
	//转向购物车页面
	@RequestMapping("/show")
	public String toCart(Model model) throws Exception{
		Long userId=UserThreadLocal.getUserId();
		List<Cart> cartList=cartService.queryCartById(userId);
		model.addAttribute("cartList",cartList);		
		return "cart";
	}
	//添加商品到购物车
	@RequestMapping("/add/{itemId}")
	public String addCart(Cart cart) throws Exception{
		Long userId=UserThreadLocal.getUserId();
		cart.setUserId(userId);
		cartService.saveCart(cart);
		return "redirect:/cart/show.html";
		
	}
	//更新购物车
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public String updateCart(Cart cart) throws Exception{
		Long userId=UserThreadLocal.getUserId();
		cart.setUserId(userId);
		cartService.updateCart(cart);
		return "";
	}
	
	@RequestMapping("/delete/{itemId}")	
	public String deleteCart(Cart cart) throws Exception{
		Long userId=UserThreadLocal.getUserId();
		cart.setUserId(userId);
		cartService.deleteCart(cart);
		return "redirect:/cart/show.html";
	}
}
