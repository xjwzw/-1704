package com.jt.order.pojo;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;
@Table(name="tb_order_item")
public class OrderItem implements Serializable{
	@Id
	private String itemId;
	@Id
	private String orderId;
	private Integer num;
	private Long price;
	private String totalFee;
	private String picPath;
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	
	
	
}
