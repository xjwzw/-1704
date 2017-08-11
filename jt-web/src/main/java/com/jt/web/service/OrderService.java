package com.jt.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.web.pojo.Cart;
import com.jt.web.pojo.Orders;

@Service
public class OrderService {
	@Autowired
	private HttpClientService httpClientService;
	private static final ObjectMapper mapper=new ObjectMapper();
	//订单提交
	public String creat(Orders order) throws Exception {
		String url="http://order.jt.com/order/create";
		String jsonData=mapper.writeValueAsString(order);		
		String json=httpClientService.doPostJson(url, jsonData);
		JsonNode jsonNode=mapper.readTree(json);
		String orderId=jsonNode.get("data").asText();		
		return orderId;
	}
	//查询购物车
	public List<Cart> queryOrderByUserId(Long userId) throws Exception {
		String url="http://cart.jt.com/cart/query/"+userId;
		String jsonData=httpClientService.doGet(url);
		JsonNode jsonNode=mapper.readTree(jsonData);
		JsonNode data=jsonNode.get("data");
		Object obj = null;
        if (data.isArray() && data.size() > 0) {
            obj = mapper.readValue(data.traverse(),
                    mapper.getTypeFactory().constructCollectionType(List.class, Cart.class));
        }		
		return (List<Cart>) obj;
	}
	//根据订单id查询订单
	public Orders queryOrderByOrderId(String id) throws Exception {
		String url="http://order.jt.com/order/query/"+id;
		String jsonData=httpClientService.doGet(url);		
		return mapper.readValue(jsonData, Orders.class);
	}
	
}
