package com.jt.cart.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.cart.mapper.CartMapper;
import com.jt.cart.pojo.Cart;
import com.jt.common.service.BaseService;
@Service
public class CartService extends BaseService<Cart>{
	@Autowired
	private CartMapper cartMapper;
	//我的购物车
	public List<Cart> queryByUserId(Long userId){
		Cart cart=new Cart();
		cart.setUserId(userId);
		//利用pojo对象来传递where条件参数，属性不为null的才传递
		List<Cart> cartList= cartMapper.select(cart);
		return cartList;
	}
	//加入购物车
	public void saveCart(Cart cart){
		cart.setCreated(new Date());
		cart.setUpdated(cart.getCreated());
		//判断此用户的此商品是否存在,存在直接累加数量=旧的商品数量+新的商品数量
		//不存在，直接添加进数据库
		Cart param=new Cart();
		param.setUserId(cart.getUserId());
		param.setItemId(cart.getItemId());
		Cart curCart=super.queryByWhere(param);
		if(curCart!=null){
			curCart.setNum(curCart.getNum()+cart.getNum());
			cartMapper.updateByPrimaryKey(curCart);
		}else{		
		cartMapper.insertSelective(cart);
		}
	}
	//更新商品数量
	public void updateCart(Cart cart){
		cartMapper.updateNum(cart);
	}
	//删除商品
	public void deleteCart(Cart cart){
		cartMapper.delete(cart);
	}
}
