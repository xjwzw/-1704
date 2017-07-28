package com.jt.manage.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jt.common.service.BaseService;
import com.jt.common.vo.EasyUIResult;
import com.jt.manage.mapper.ItemDescMapper;
import com.jt.manage.mapper.ItemMapper;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;

@Service
public class ItemService extends BaseService<Item>{
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;
	/**
	 * {"title":2000,"rows":[{},{},{}]}
		title 是记录总数
		rows表示查询的数据
	 * @param page
	 * @param rows
	 * @return
	 * @throws JsonProcessingException 
	 */
	public EasyUIResult findItemList(int page, int rows) {
		//使用分页插件进行分页     page:查询的页数    rows 查询的数据量  
		//分页的开关
		PageHelper.startPage(page, rows);
		List<Item> itemList=itemMapper.findItemList();
		//自己计算全部的信息数
		PageInfo<Item> info=new PageInfo<Item>(itemList);
		
		return new EasyUIResult(info.getTotal(), info.getList());
		/**
		 * 手动的分页配置
		int title = itemMapper.selectItemCount();
		//分页的开始行数
		int startNum = (page-1)*rows;
		
		List<Item> itemList = itemMapper.findPageInfoList(startNum,rows);
		EasyUIResult result = new EasyUIResult();
		result.setTotal(title);
		result.setRows(itemList);
		return  result;
		
		//将数据转化为JSON串
		/*ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(itemList);
		*/		
	}
	public String findItemCatName(Long itemCatId) {
		
		return itemMapper.findItemCatName(itemCatId);
	}
	/**
	 * 新增商品信息
	 * @param item
	 */
	public void saveItem(Item item,String desc) {
		//itemMapper.insert(record) 全部插入，即使对象中属性为null也会进行插入操作
		//动态插入
		item.setCreated(new Date());
		item.setUpdated(item.getCreated());
		itemMapper.insertSelective(item);		
		/**
		 * 问题：商品描述信息中需要进入库操作，但入库操作主键为商品主键id
		 * 但是，商品处于要插入状态，mysql还没有为其分配id值，所以现在的操作拿不到id
		 * 解决方案：
		 * mybatis+通用mapper+mysql的具体实现 若换成oracle则不能运行
		 * itemMapper.insertSelective(item)后又自己查询了新增的操作
		 * 
		 */
		
		ItemDesc itemDesc=new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(item.getCreated());
		itemDesc.setUpdated(item.getUpdated());
		
		itemDescMapper.insertSelective(itemDesc);
	}
	/**
	 * 修改商品信息
	 * @param item
	 */
	
	public void updateItem(Item item,String desc) {
		item.setUpdated(new Date());
		itemMapper.updateByPrimaryKeySelective(item);
		ItemDesc itemDesc=new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(item.getUpdated());
		itemDescMapper.updateByPrimaryKeySelective(itemDesc);
	}
	/**
	 * 删除商品
	 * @param ids
	 */
	public void deleteItem(Long[] ids) {
		itemMapper.deleteByIDS(ids);
		itemDescMapper.deleteByIDS(ids);
	}
	/**
	 * 批量下架商品
	 * @param id
	 */
	public void updateItemStatus(Long[] ids) {
		for (Long id : ids) {
			Item item=new Item();
			item.setId(id);
			item.setStatus(2);
			itemMapper.updateByPrimaryKeySelective(item);
		}
			
		
		
	}
	public int findCount() {
		return itemMapper.findCount();
	}
	public void updateItemStatusToReshelf(Long[] ids) {
		for(Long id:ids){
			Item item=new Item();
			item.setId(id);
			item.setStatus(1);
			itemMapper.updateByPrimaryKeySelective(item);
		}
	}
	public ItemDesc findItemDesc(Long itemId) {
		
		return itemDescMapper.selectByPrimaryKey(itemId);
	}
	
}
