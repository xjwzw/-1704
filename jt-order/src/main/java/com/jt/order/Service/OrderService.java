package com.jt.order.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.common.service.BaseService;
import com.jt.order.mapper.OrderMapper;
import com.jt.order.pojo.Orders;

@Service
public class OrderService extends BaseService<Orders>{
	@Autowired
	private OrderMapper orderMapper;
	//订单新增
	public String create(Orders order){
		String orderId=order.getUserId()+""+System.currentTimeMillis();
		order.setOrderId(orderId);
		orderMapper.create(order);
		return orderId;
	}
	//查询订单
	public Orders queryOrdersByOrderId(String orderId) {
		return orderMapper.queryOrdersByOrderId(orderId);
	}
}
