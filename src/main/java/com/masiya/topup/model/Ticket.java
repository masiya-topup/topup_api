package com.masiya.topup.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Ticket {

	private Integer ticketId;
	private String ticketTitle;
	private String ticketDesc;
	private Date ticketOpenDate;
	private Date ticketCloseDate;
	private String ticketStatus;
	private User user;
	private User owner;
	
	public Integer getTicketId() {
		return ticketId;
	}
	public void setTicketId(Integer ticketId) {
		this.ticketId = ticketId;
	}
	public String getTicketTitle() {
		return ticketTitle;
	}
	public void setTicketTitle(String ticketTitle) {
		this.ticketTitle = ticketTitle;
	}
	public String getTicketDesc() {
		return ticketDesc;
	}
	public void setTicketDesc(String ticketDesc) {
		this.ticketDesc = ticketDesc;
	}
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Kuwait")
	public Date getTicketOpenDate() {
		return ticketOpenDate;
	}
	public void setTicketOpenDate(Date ticketOpenDate) {
		this.ticketOpenDate = ticketOpenDate;
	}
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Kuwait")
	public Date getTicketCloseDate() {
		return ticketCloseDate;
	}
	public void setTicketCloseDate(Date ticketCloseDate) {
		this.ticketCloseDate = ticketCloseDate;
	}
	public String getTicketStatus() {
		return ticketStatus;
	}
	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
}
