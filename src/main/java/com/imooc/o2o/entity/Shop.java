package com.imooc.o2o.entity;

import java.util.Date;
/**
 * 
 *  店铺类
 * @author Administrator
 *
 */
public class Shop {
	private Long shopId; //商家id
	private String shopName; // 商家名称
	private String shopDesc; // 对商家的描述
	private String shopAddr; // 商家的地址
	private String phone; // 商家的电话
	private String shopImg; // 商家的封面的图片的url 
	private Integer priority; // 商家的权重 也就是优先级显示
	private Date createTime; // 创建时间
	private Date lastEditTime; // 修改时间
	// -1：不可用 0:审核中 1：可用
	private Integer enableStatus; //店铺的状态
	private String advice; // 对商家的建议（超级管理员）
	private Area area; // 商家所处的地理位置
	private PersonInfo owner; // 使用的用户信息
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopDesc() {
		return shopDesc;
	}
	public void setShopDesc(String shopDesc) {
		this.shopDesc = shopDesc;
	}
	public String getShopAddr() {
		return shopAddr;
	}
	public void setShopAddr(String shopAddr) {
		this.shopAddr = shopAddr;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getShopImg() {
		return shopImg;
	}
	public void setShopImg(String shopImg) {
		this.shopImg = shopImg;
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
	public Integer getEnableStatus() {
		return enableStatus;
	}
	public void setEnableStatus(Integer enableStatus) {
		this.enableStatus = enableStatus;
	}
	public String getAdvice() {
		return advice;
	}
	public void setAdvice(String advice) {
		this.advice = advice;
	}
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public PersonInfo getOwner() {
		return owner;
	}
	public void setOwner(PersonInfo owner) {
		this.owner = owner;
	}
	public ShopCategory getShopCategory() {
		return shopCategory;
	}
	public void setShopCategory(ShopCategory shopCategory) {
		this.shopCategory = shopCategory;
	}
	private ShopCategory shopCategory;
	public void setParentCategory(ShopCategory parentCategory) {
		// TODO Auto-generated method stub
		
	}
	public Long getOwnerId() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
