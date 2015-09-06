package com.masiya.topup.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EventLog {
	public enum LogType {
		api ("api"),
		customercare ("customercare"),
		user ("user");
	
		private final String name;       
		
		private LogType(String s) {
			name = s;
		}
		
		public boolean equalsName(String otherName) {
			return (otherName == null) ? false : name.equals(otherName);
		}
		
		public String toString() {
			return this.name;
		}
	}

	private Integer eventLogId;
	private LogType eventLogType;
	private String eventLogTitle;
	private String eventLogDetails;
	private Date eventLogDate;
	private User user;
	private User owner;
	
	public Integer getEventLogId() {
		return eventLogId;
	}
	public void setEventLogId(Integer eventLogId) {
		this.eventLogId = eventLogId;
	}
	public LogType getEventLogType() {
		return eventLogType;
	}
	public void setEventLogType(LogType eventLogType) {
		this.eventLogType = eventLogType;
	}
	public String getEventLogTitle() {
		return eventLogTitle;
	}
	public void setEventLogTitle(String eventLogTitle) {
		this.eventLogTitle = eventLogTitle;
	}
	public String getEventLogDetails() {
		return eventLogDetails;
	}
	public void setEventLogDetails(String eventLogDetails) {
		this.eventLogDetails = eventLogDetails;
	}
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Kuwait")
	public Date getEventLogDate() {
		return eventLogDate;
	}
	public void setEventLogDate(Date eventLogDate) {
		this.eventLogDate = eventLogDate;
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
