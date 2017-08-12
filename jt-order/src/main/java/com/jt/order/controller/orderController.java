package com.jt.order.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.mapper.SysMapper;
import com.jt.common.vo.SysResult;
import com.jt.order.Service.OrderService;
import com.jt.order.pojo.Orders;

@Controller
@RequestMapping("/order")
public class orderController {
	@Autowired
	private OrderService orderService;
	private static final ObjectMapper mapper=new ObjectMapper();
	
	//新增订单
	@RequestMapping("/create")
	@ResponseBody
	public SysResult create(@RequestBody String json) throws JsonProcessingException, IOException{
		Orders orders=mapper.readValue(json, Orders.class);
		String orderId=orderService.create(orders);		
		return SysResult.oK(orderId);
		
	}
	
	//根据id查询订单
	@RequestMapping("/query/{orderId}")
	@ResponseBody
	public Orders queryOrderById(@PathVariable String orderId){
		return orderService.queryOrdersByOrderId(orderId);
	}
	
	@RequestMapping("/changeOrderStatus")
	@ResponseBody
	public SysResult paymentOrderScan(){
		orderService.paymentOrderScan();
		return SysResult.oK();
	}
}
