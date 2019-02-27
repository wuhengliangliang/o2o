package com.imooc.o2o.entity;

import java.util.Date;

public class WechatAuth {
	private Long wechatAuthId; //微信账号id
	private String openId; //作为微信账号与公众号绑定的唯一的标识
	private Date createTime;  // 创建时间
	private PersonInfo personInfo; //这里与用户直接相关联 创建实体类
	public Long getWechatAuthId() {
		return wechatAuthId;
	}
	public void setWechatAuthId(Long wechatAuthId) {
		this.wechatAuthId = wechatAuthId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public PersonInfo getPersonInfo() {
		return personInfo;
	}
	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}
	@Override
	public String toString() {
		return "WechatAuth [wechatAuthId=" + wechatAuthId + ", openId=" + openId + ", createTime=" + createTime
				+ ", personInfo=" + personInfo + "]";
	}
	
}
