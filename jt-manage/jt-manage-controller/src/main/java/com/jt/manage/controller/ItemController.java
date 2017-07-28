package com.jt.manage.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	//引入日志文件
	private static final Logger logger=Logger.getLogger(ItemController.class);
	
	@Autowired
	private ItemService itemService;
	/*
	 * easyUI 的全部请求都是AJAX提交
	 * 值的传递是以json形式进行的
	 * 分页的参数
	 * http://localhost:8091/item/query?page=1&rows=20
	 * page 第几页
	 * rows 显示的行数
	 * {"title":2000,"rows":[{},{},{}]} easyUI的格式要求
	 */
	@RequestMapping("/query")
	@ResponseBody //将返回值直接转化为JSON串  [{},{},{},{}]
	public EasyUIResult findItemList(int page,int rows){
		return itemService.findItemList(page,rows);
	}
	
	
	//根据商品分类ID查询商品名称
	@RequestMapping("/queryItemName")
	public void findItemCatName(Long itemCatId,HttpServletResponse response){
		String name=itemService.findItemCatName(itemCatId);
		response.setContentType("text/html;charset=utf-8");
		try {
			response.getWriter().write(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveItem(Item item,String desc){
		//SysResult sysResult=new SysResult();
		try {			
			itemService.saveItem(item,desc);
			/*sysResult.setStatus(200);//正确返回
			sysResult.setMsg("新增商品成功");*/
			return SysResult.build(200, "商品新增成功");
		} catch (Exception e) {
			e.printStackTrace();
			//生成日志消息
			logger.error("~~~~~~~~新增商品失败"+e.getMessage());
			return SysResult.build(201,"新增商品失败!请联系管理员");
		}
	}
	@RequestMapping("/update")
	@ResponseBody
	public SysResult updateItem(Item item,String desc){
		try {			
			itemService.updateItem(item,desc);
			/*sysResult.setStatus(200);//正确返回
			sysResult.setMsg("新增商品成功");*/
			return SysResult.build(200, "商品修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			//生成日志消息
			logger.error("~~~~~~~~修改商品失败"+e.getMessage());
			return SysResult.build(201,"修改商品失败!请联系管理员");
		}
	}
	@RequestMapping("delete")
	@ResponseBody
	public SysResult deleteItem(Long[] ids){
		try {			
			itemService.deleteItem(ids);
			/*sysResult.setStatus(200);//正确返回
			sysResult.setMsg("新增商品成功");*/
			return SysResult.build(200, "商品删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			//生成日志消息
			logger.error("~~~~~~~~删除商品失败"+e.getMessage());
			return SysResult.build(201,"删除商品失败!请联系管理员");
		}
	}
	@RequestMapping("instock")
	@ResponseBody
	public SysResult updateItemStatus(Long[] ids){
		try {			
			itemService.updateItemStatus(ids);
			/*sysResult.setStatus(200);//正确返回
			sysResult.setMsg("新增商品成功");*/
			return SysResult.build(200, "商品下架成功");
		} catch (Exception e) {
			e.printStackTrace();
			//生成日志消息
			logger.error("~~~~~~~~下架商品失败"+e.getMessage());
			return SysResult.build(201,"下架商品失败!请联系管理员");
		}
	}
	@RequestMapping("reshelf")
	@ResponseBody
	public SysResult updateItemStatus2(Long[] ids){
		try {			
			itemService.updateItemStatusToReshelf(ids);
			/*sysResult.setStatus(200);//正确返回
			sysResult.setMsg("新增商品成功");*/
			return SysResult.build(200, "商品上架成功");
		} catch (Exception e) {
			e.printStackTrace();
			//生成日志消息
			logger.error("~~~~~~~~上架商品失败"+e.getMessage());
			return SysResult.build(201,"上架商品失败!请联系管理员");
		}
	}
	/**
	 * 查询商品描述信息
	 * @param itemId
	 * @return
	 */
	@RequestMapping("/desc/{itemId}")
	@ResponseBody
	public SysResult findItemDesc(@PathVariable Long itemId){
		try {			
			ItemDesc itemDesc=itemService.findItemDesc(itemId);		
			return SysResult.oK(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
			//生成日志消息
			logger.error("~~~~~~~~查询商品描述信息失败"+e.getMessage());
			return SysResult.build(201,"查询商品描述信息失败!请联系管理员");
		}
	}
	
	
}
