package com.masiya.topup.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Transaction {
	private Integer transactionId;
	private String transactionType;
	private String transactionSystem;
	private Double transactionAmount;
	private String transactionRefId;
	private String transactionTrackId;
	private String transactionPaymentId;
	private Date transactionDate;
	private String transactionStatus;
	private String userEmail;
	private Company company;
	private Category category;
	private Service service;
	private User user;
	private UserPhone phone;
	
	public Transaction() {
		super();
		this.transactionStatus = "transit";
	}
	public Integer getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getTransactionSystem() {
		return transactionSystem;
	}
	public void setTransactionSystem(String transactionSystem) {
		this.transactionSystem = transactionSystem;
	}
	public Double getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public String getTransactionRefId() {
		return transactionRefId;
	}
	public void setTransactionRefId(String transactionRefId) {
		this.transactionRefId = transactionRefId;
	}
	public String getTransactionTrackId() {
		return transactionTrackId;
	}
	public void setTransactionTrackId(String transactionTrackId) {
		this.transactionTrackId = transactionTrackId;
	}
	public String getTransactionPaymentId() {
		return transactionPaymentId;
	}
	public void setTransactionPaymentId(String transactionPaymentId) {
		this.transactionPaymentId = transactionPaymentId;
	}
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Kuwait")
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Service getService() {
		return service;
	}
	public void setService(Service service) {
		this.service = service;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public UserPhone getPhone() {
		return phone;
	}
	public void setPhone(UserPhone phone) {
		this.phone = phone;
	}

}
