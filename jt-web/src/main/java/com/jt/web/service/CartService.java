package com.jt.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.web.pojo.Cart;

@Service
public class CartService {
	@Autowired
	private HttpClientService httpClientService;
	private static final ObjectMapper mapper=new ObjectMapper();
	public List<Cart> queryCartById(Long userId) throws Exception{
		String url="http://cart.jt.com/cart/query/"+userId;
		String jasonData=httpClientService.doGet(url);
		JsonNode jsonNode=mapper.readTree(jasonData);
		JsonNode cartListJsonNode=jsonNode.get("data");
		Object obj = null;
        if (cartListJsonNode.isArray() && cartListJsonNode.size() > 0) {
            obj = mapper.readValue(cartListJsonNode.traverse(),
                    mapper.getTypeFactory().constructCollectionType(List.class, Cart.class));
        }
		return (List<Cart>) obj;
		
	}
	public void saveCart(Cart cart) throws Exception {
		String url="http://cart.jt.com/cart/save";
		Map<String,String> map=new HashMap<String,String>();
		map.put("userId", cart.getUserId()+"");
		map.put("itemId", cart.getItemId()+"");
		map.put("itemTitle", cart.getItemTitle());
		map.put("itemImage",cart.getItemImage());
		map.put("itemPrice", "");
		map.put("num", cart.getNum()+"");
		httpClientService.doPost(url, map,"utf-8");
		
		
	}
	public void updateCart(Cart cart) throws Exception {
		String url="http://cart.jt.com/cart/update/num/"+cart.getUserId()+"/"+cart.getItemId()+"/"+cart.getNum();
		httpClientService.doGet(url);
	}
	public void deleteCart(Cart cart) throws Exception {
		String url="http://cart.jt.com/cart/delete/"+cart.getUserId()+"/"+cart.getItemId();
		httpClientService.doGet(url);
	}
}
