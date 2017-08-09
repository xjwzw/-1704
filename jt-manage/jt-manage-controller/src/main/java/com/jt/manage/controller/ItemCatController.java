package com.jt.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.ItemCatResult;
import com.jt.common.vo.SysResult;
import com.jt.manage.pojo.ItemCat;
import com.jt.manage.service.ItemCatService;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	//当不传任何值时表示一级标题 parentId=0
	@RequestMapping("list")
	@ResponseBody
	public List<ItemCat> findItemCatList(@RequestParam(defaultValue="0") Long id){		
		return itemCatService.findItemCatList(id);
		
	}
	
	
	
}
