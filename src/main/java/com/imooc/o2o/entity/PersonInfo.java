package com.imooc.o2o.entity;

import java.util.Date;

public class PersonInfo {
	private Long userId; //用户id
	private String name;// 用户姓名
	private String profileImg;// 用户头像地址
	private String email;// 邮箱
	private String gender;// 性别
	private Integer enableStatus;// 用户状态
//这里我们默认设置 1是顾客  2是店家  3是超级管理员
	private Integer useType; //用户类型
	private Date createTime; //创建时间
	private Date lastEditTime; //修改时间
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProfileImg() {
		return profileImg;
	}
	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Integer getEnableStatus() {
		return enableStatus;
	}
	public void setEnableStatus(Integer enableStatus) {
		this.enableStatus = enableStatus;
	}
	public Integer getUseType() {
		return useType;
	}
	public void setUseType(Integer useType) {
		this.useType = useType;
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
	@Override
	public String toString() {
		return "PersonInfo [userId=" + userId + ", name=" + name + ", profileImg=" + profileImg + ", email=" + email
				+ ", gender=" + gender + ", enableStatus=" + enableStatus + ", useType=" + useType + ", createTime="
				+ createTime + ", lastEditTime=" + lastEditTime + "]";
	}

	
	

}
