package com.imooc.o2o.dto;

import java.util.List;

import org.apache.jasper.tagplugins.jstl.core.ForEach;

import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.enums.ProductCategoryStateEnum;

public class ProductCategoryExecution {
 //状态
	private int state;
	//
	private String stateInfo; // 状态信息
	private List<ProductCategory>productCategoryList;
	//默认的构造器
	public ProductCategoryExecution() {
		
	}
	//操作失败的时候使用得当构造器
	public ProductCategoryExecution(ProductCategoryStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();		
	}
	public ProductCategoryExecution(ProductCategoryStateEnum stateEnum,List<ProductCategory>productCategory) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.productCategoryList = productCategoryList;
		
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
	public List<ProductCategory> getProductCategoryList() {
		return productCategoryList;
	}
	public void setProductCategoryList(List<ProductCategory> productCategoryList) {
		this.productCategoryList = productCategoryList;
	}
}
