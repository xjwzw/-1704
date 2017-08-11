package com.jt.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.web.Treadlocal.UserThreadLocal;
import com.jt.web.pojo.Cart;
import com.jt.web.pojo.Orders;
import com.jt.web.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderService orderService;	
	//转向订单页面
	@RequestMapping("create")
	public String orderCreate(Model model) throws Exception{
		Long userId=7L;		
		List<Cart> cartList=orderService.queryOrderByUserId(userId);
		model.addAttribute("carts",cartList);
		return "order-cart";
	}
	
	
	//提交订单
	@RequestMapping("/submit")
	@ResponseBody
	public SysResult submit(Orders order) throws Exception{
		Long userId=7L;
		order.setUserId(userId);
		String orderId=orderService.creat(order);
		return SysResult.oK(orderId);
	}
	
	//转向成功页面
	@RequestMapping("/success")
	public String success(String id,Model model) throws Exception{
		Orders order=orderService.queryOrderByOrderId(id);
		model.addAttribute("order",order);
		return "success";
	}
	
}
