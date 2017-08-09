package com.jt.manage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.BaseService;
import com.jt.common.service.RedisService;
import com.jt.common.vo.ItemCatData;
import com.jt.common.vo.ItemCatResult;
import com.jt.manage.mapper.ItemCatMapper;
import com.jt.manage.pojo.ItemCat;
import com.mysql.jdbc.StringUtils;

import redis.clients.jedis.JedisCluster;

@Service
public class ItemCatService extends BaseService<ItemCat>{
	@Autowired
	private ItemCatMapper itemCatMapper;
	
	@Autowired
	private RedisService redisService;
	@Autowired
	private JedisCluster JedisCluster;
	//引入java对象和json串转换对象ObjectMapper；全局唯一
	private static final ObjectMapper MAPPER=new ObjectMapper(); 
	public List<ItemCat> findItemCatList(Long parentId){
		/*
		 * 商品分类要使用缓存步骤：
		 * 1）先判断缓存中是否有数据，如果有数据就读取，直接返回
		 * 2）如果缓存中没有数据，要继续执行业务，不能抛出异常
		 * 3）执行完业务，要多一步动作，要把结果放入缓存中string，先把java对象转换成json串，kv写入缓存中。
		*/	
		String item_cat_key="ITEMCAT_KEY"+parentId;
		ItemCat params=new ItemCat();
		params.setStatus(1);
		params.setParentId(parentId);
		//从redis中获取数据
		try {
			String jasonData=redisService.get(item_cat_key);
			if(!StringUtils.isNullOrEmpty(jasonData)){
				JsonNode jsNode =MAPPER.readTree(jasonData);//把json串转换JsonNode
				//利用jackson提供方法，将json串转成java对象，List<Object>
				List<ItemCat> itemCatList=MAPPER.readValue(jsNode.traverse(), 
						MAPPER.getTypeFactory().constructCollectionType(List.class, ItemCat.class));												
				return itemCatList;
			}
			
			/**
			 * 如果传入的是对象,那么查询时就会根据对象的属性值添加where条件
			 */
			ItemCat itemCat=new ItemCat();
			itemCat.setParentId(parentId);
			List<ItemCat> itemCatList= itemCatMapper.select(itemCat);
			jasonData=MAPPER.writeValueAsString(itemCatList);
			redisService.set(item_cat_key, jasonData);
			return itemCatList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	
	public ItemCatResult getItemCat(){
		/*
		 * 步骤：
		 * 1、获取所有的数据
		 * 2、一次循环获取当前节点的所有的子节点
		 * 3、三级遍历组织数据
		 */
		
		List<ItemCat> cats=itemCatMapper.select(null);
		Map<Long,List<ItemCat>> map=new HashMap<Long,List<ItemCat>>();
		for (ItemCat itemCat : cats) {
			//判断这个key是否存在
			if(!map.containsKey(itemCat.getParentId()))
			{  
				//不存在 创建
				map.put(itemCat.getParentId(),new ArrayList<ItemCat>());
				
			}
			map.get(itemCat.getParentId()).add(itemCat);
		}
		
		ItemCatResult result=new ItemCatResult();
		List<ItemCatData> list1=new ArrayList<ItemCatData>();
		String url="";
		String name="";
		//遍历一级菜单
		for (ItemCat itemCat1 :map.get(0L) ) {
			ItemCatData d=new ItemCatData();  
			url="/products/"+itemCat1.getId()+".html";
			 d.setUrl(url);
			name="<a href=\""+url+"\">"+itemCat1.getName()+"</a>";
			d.setName(name);
			
		//遍历二级菜单	
			List<ItemCatData> list2=new ArrayList<ItemCatData>();
			for (ItemCat itemCat2 :map.get(itemCat1.getId()) ) {
				ItemCatData d2=new ItemCatData();  
				url="/products/"+itemCat2.getId()+".html";
				 d2.setUrl(url);				
				d2.setName(itemCat2.getName());
				List<String> list3=new ArrayList<String>();
				for (ItemCat itemCat3 :map.get(itemCat2.getId()) ) {
					ItemCatData d3=new ItemCatData();  
					url="/products/"+itemCat3.getId()+".html";
				    list3.add(url+"|"+itemCat3.getName());
				}
			d2.setItems(list3);
			list2.add(d2);
						
			}
		  d.setItems(list2);
		  list1.add(d);
		 result.setItemCats(list1);		
		}
		return result;
	}
	
	
	
}
