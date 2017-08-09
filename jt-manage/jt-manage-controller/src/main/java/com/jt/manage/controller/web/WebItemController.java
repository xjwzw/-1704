package com.jt.manage.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.service.ItemService;

@Controller
//查询商品详情
public class WebItemController {
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("web/item/{itemId}")
	@ResponseBody
	public Item getItemByItemId(@PathVariable Long itemId){
		
		return itemService.queryById(itemId);
		
	}
	
	@RequestMapping("web/item/desc/{itemId}")
	@ResponseBody
	public ItemDesc getItemDescByItemId(@PathVariable Long itemId){
		
		return itemService.findItemDesc(itemId);
	}
	
}
