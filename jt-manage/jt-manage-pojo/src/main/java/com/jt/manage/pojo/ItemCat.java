package com.jt.manage.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jt.common.po.BasePojo;
@Table(name="tb_item_cat")
@JsonIgnoreProperties(ignoreUnknown=true)
public class ItemCat extends BasePojo{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;//分类id
	private Long parentId;//上级分类id
	private String name;//分类名称
	private Integer status;//默认值为1，可选值：1正常，2删除
	private Integer sortOrder;//排序号
	private Boolean isParent;//表示是否为上级目录
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	public Boolean getIsParent() {
		return isParent;
	}
	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}
	/**
	 * 为了满足easyUI的树形结构，添加get方法 text  state:"closed" open
	 * 
	 */
	public String getText(){
		return name;
	}
	/**
	 * 如果是上级菜单，closed
	 * 如果不是上级菜单，open
	 * @return
	 */
	public String getState(){
		return isParent?"closed":"open";
	}
}
