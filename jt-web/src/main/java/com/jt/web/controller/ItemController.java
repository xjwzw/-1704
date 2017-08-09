package com.jt.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.web.pojo.Item;
import com.jt.web.pojo.ItemDesc;
import com.jt.web.service.ItemService;

@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	@RequestMapping("/items/{itemId}")
	public String getItemById(@PathVariable Long itemId,Model model){
	      Item item=itemService.getItemById(itemId);
	      
	      model.addAttribute("item",item);
	      
	      ItemDesc itemDesc=itemService.getItemDescByItemId(itemId);
	      model.addAttribute("itemDesc",itemDesc);
	      return "item";
	}
}
