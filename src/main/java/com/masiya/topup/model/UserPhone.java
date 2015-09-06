package com.masiya.topup.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UserPhone {

	private Integer userPhoneId;
	private String userPhoneNo;
	private String userPhoneType;
	private Date userPhoneDate;
	private User user;
	
	public Integer getUserPhoneId() {
		return userPhoneId;
	}
	public void setUserPhoneId(Integer userPhoneId) {
		this.userPhoneId = userPhoneId;
	}
	public String getUserPhoneNo() {
		return userPhoneNo;
	}
	public void setUserPhoneNo(String userPhoneNo) {
		this.userPhoneNo = userPhoneNo;
	}
	public String getUserPhoneType() {
		return userPhoneType;
	}
	public void setUserPhoneType(String userPhoneType) {
		this.userPhoneType = userPhoneType;
	}
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Kuwait")
	public Date getUserPhoneDate() {
		return userPhoneDate;
	}
	public void setUserPhoneDate(Date userPhoneDate) {
		this.userPhoneDate = userPhoneDate;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
