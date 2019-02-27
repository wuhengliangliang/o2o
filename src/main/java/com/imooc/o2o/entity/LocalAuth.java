package com.imooc.o2o.entity;

import java.util.Date;

public class LocalAuth {
	private Long localAuthId; //本地id
	private String username; // 用户名
	private String passwoed;//用户密码
	private Date createTime;// 创建时间
	private Date lastEditTime;// 修改时间
	private PersonInfo personInfo;// 用户信息
	public Long getLocalAuthId() {
		return localAuthId;
	}
	public void setLocalAuthId(Long localAuthId) {
		this.localAuthId = localAuthId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasswoed() {
		return passwoed;
	}
	public void setPasswoed(String passwoed) {
		this.passwoed = passwoed;
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
	public PersonInfo getPersonInfo() {
		return personInfo;
	}
	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}
	@Override
	public String toString() {
		return "LocalAuth [localAuthId=" + localAuthId + ", username=" + username + ", passwoed=" + passwoed
				+ ", createTime=" + createTime + ", lastEditTime=" + lastEditTime + ", personInfo=" + personInfo + "]";
	}
	
}
