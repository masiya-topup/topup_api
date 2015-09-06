package com.masiya.topup.model;

import java.util.Date;

public class PasswordReset {
	private Integer passwordResetId;
	private String passwordResetHash;
	private Date passwordResetDate;
	private User user;
	
	public Integer getPasswordResetId() {
		return passwordResetId;
	}
	public void setPasswordResetId(Integer passwordResetId) {
		this.passwordResetId = passwordResetId;
	}
	public String getPasswordResetHash() {
		return passwordResetHash;
	}
	public void setPasswordResetHash(String passwordResetHash) {
		this.passwordResetHash = passwordResetHash;
	}
	public Date getPasswordResetDate() {
		return passwordResetDate;
	}
	public void setPasswordResetDate(Date passwordResetDate) {
		this.passwordResetDate = passwordResetDate;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

}
