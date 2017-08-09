package com.jt.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.service.RedisService;
import com.jt.common.spring.exetend.PropertyConfig;
import com.jt.web.pojo.Item;
import com.jt.web.pojo.ItemDesc;
@Service
public class ItemService {
	@Autowired
	private HttpClientService httpClientService;
	@PropertyConfig
	private String MANAGE_URL;
	@PropertyConfig
	private String ITEM_KEY_PEFIX;
	@PropertyConfig
	private String ITEMDESC_KEY_PEFIX;
	@Autowired
	private RedisService redisService;
	private static final ObjectMapper mapper=new ObjectMapper();
	public Item getItemById(Long itemId) {
		String url="http://manage.jt.com/web/item/"+itemId;;
		try {
			String ITEM_KEY=ITEM_KEY_PEFIX+itemId;
			String jasonData=redisService.get(ITEM_KEY);
			if(StringUtils.isEmpty(jasonData)){			//判断缓存是否有数据
				//缓存无数据
				jasonData=httpClientService.doGet(url);
				redisService.set(ITEM_KEY, jasonData);//设置缓存
			}
			Item item=mapper.readValue(jasonData, Item.class);
			return item;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	public ItemDesc getItemDescByItemId(Long itemId) {
		String url=MANAGE_URL+"/web/item/desc/"+itemId;
		try {
			String ITEMDESC_KEY=ITEMDESC_KEY_PEFIX+itemId;
			String jasonData=redisService.get(ITEMDESC_KEY);
			if(StringUtils.isEmpty(jasonData)){
				jasonData=httpClientService.doGet(url);	
				redisService.set(ITEMDESC_KEY,jasonData);
			}
			ItemDesc itemDesc=mapper.readValue(jasonData, ItemDesc.class);
			return itemDesc;
		} catch (Exception e) {
		   e.printStackTrace();
		}
		return null;
	}

}
