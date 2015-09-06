package com.masiya.topup.model;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"userAuthToken","userAuthDate","userAuthLastDate","userAuthStatus", "user"})
public class UserAuth {

	private Integer userAuthId;
	private String userAuthToken;
	private Date userAuthDate;
	private Date userAuthLastDate;
	private Byte userAuthStatus;
	private User user;
	
	public UserAuth() {
		super();
		this.userAuthToken = UUID.randomUUID().toString();
		this.userAuthDate = new Date();
		this.userAuthStatus = 1;
	}
	
	public UserAuth(User usr) {
		super();
		this.userAuthToken = UUID.randomUUID().toString();
		this.userAuthDate = new Date();
		this.userAuthStatus = 1;
		this.user = usr;
	}
	
	@JsonIgnore
	public Integer getUserAuthId() {
		return userAuthId;
	}
	public void setUserAuthId(Integer userAuthId) {
		this.userAuthId = userAuthId;
	}
	
	public String getUserAuthToken() {
		return userAuthToken;
	}
	public void setUserAuthToken(String userAuthToken) {
		this.userAuthToken = userAuthToken;
	}
	
	@JsonProperty("userLoginDate")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Kuwait")
	public Date getUserAuthDate() {
		return userAuthDate;
	}
	public void setUserAuthDate(Date userAuthDate) {
		this.userAuthDate = userAuthDate;
	}
	
	@JsonProperty("userLastLoginDate")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Kuwait")
	public Date getUserAuthLastDate() {
		return userAuthLastDate;
	}

	public void setUserAuthLastDate(Date userAuthLastDate) {
		this.userAuthLastDate = userAuthLastDate;
	}

	@JsonProperty("userLoginStatus")
	public Byte getUserAuthStatus() {
		return userAuthStatus;
	}
	public void setUserAuthStatus(Byte userAuthStatus) {
		this.userAuthStatus = userAuthStatus;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

}
