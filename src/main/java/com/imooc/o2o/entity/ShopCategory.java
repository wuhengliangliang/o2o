package com.imooc.o2o.entity;

import java.util.Date;

public class ShopCategory {
	private Long shopCategoryId;//  商品类别id
	private String shopCategoryName;// 商品类别的名称
	private String shopCategoryDesc;// 商品类别的描述
	private String shopCategoryImg; // 商品图片名称
	private Integer priority; // 商品的权重 也就是优先级的顺序
	private Date createTime;// 创建时间
	private Date lastEditTime; // 修改时间
	private ShopCategory parent; // 上级id
	public Long getShopCategoryId() {
		return shopCategoryId;
	}
	public void setShopCategoryId(Long shopCategoryId) {
		this.shopCategoryId = shopCategoryId;
	}
	public String getShopCategoryName() {
		return shopCategoryName;
	}
	public void setShopCategoryName(String shopCategoryName) {
		this.shopCategoryName = shopCategoryName;
	}
	public String getShopCategoryDesc() {
		return shopCategoryDesc;
	}
	public void setShopCategoryDesc(String shopCategoryDesc) {
		this.shopCategoryDesc = shopCategoryDesc;
	}
	public String getShopCategoryImg() {
		return shopCategoryImg;
	}
	public void setShopCategoryImg(String shopCategoryImg) {
		this.shopCategoryImg = shopCategoryImg;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastEditTime() {
		return lastEditTime;
	}
	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
	public ShopCategory getParent() {
		return parent;
	}
	public void setParent(ShopCategory parent) {
		this.parent = parent;
	}
	@Override
	public String toString() {
		return "ShopCategory [shopCategoryId=" + shopCategoryId + ", shopCategoryName=" + shopCategoryName
				+ ", shopCategoryDesc=" + shopCategoryDesc + ", shopCategoryImg=" + shopCategoryImg + ", priority="
				+ priority + ", createTime=" + createTime + ", lastEditTime=" + lastEditTime + ", parent=" + parent
				+ "]";
	}
	public Long getParentId() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
