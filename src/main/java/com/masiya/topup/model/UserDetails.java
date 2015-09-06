package com.masiya.topup.model;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

@Alias("UserDetails")
public class UserDetails {
	private User user;
	private List<UserPhone> userPhones;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<UserPhone> getUserPhones() {
		return userPhones;
	}
	public void setUserPhones(List<UserPhone> userPhones) {
		this.userPhones = userPhones;
	}
}
