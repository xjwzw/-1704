package com.jt.web.pojo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.solr.client.solrj.beans.Field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jt.common.po.BasePojo;

//商品信息
@JsonIgnoreProperties(ignoreUnknown=true)
public class Item extends BasePojo{	
	@Field
	private Long id;//商品id
	@Field
	private String title;//商品标题
	//@Column(name="sell_point")
	@Field
	private String sellPoint;//卖点
	@Field
	private Long price;//价格 后期由js计算/100
	@Field
	private Integer num;//数量
	@Field
	private String barcode;//扫描码
	@Field
	private String image;//商品图片
	private Long cid;//分类号
	private Integer status;//状态 默认值为1，可选值：1正常，2下架，3删除
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSellPoint() {
		return sellPoint;
	}
	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String[] getImages(){
		return this.image.split(",");
	}
}
