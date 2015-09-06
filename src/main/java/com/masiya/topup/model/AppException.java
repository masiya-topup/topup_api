package com.masiya.topup.model;

import javax.ws.rs.WebApplicationException;

public class AppException {
	private int status=500;
	private String statusInfo="Internal Server Error";
	private String message;
	private String errorCode;

	public AppException() {
	}

	public AppException(Throwable ex) {
		this.message = ex.getMessage();
	}

	public AppException(Exception ex) {
		this.message = ex.getMessage();
	}

	public AppException(WebApplicationException ex) {
		this.status = ex.getResponse().getStatus();
		this.statusInfo = ex.getResponse().getStatusInfo().toString();
		this.message = ex.getMessage();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusInfo() {
		return statusInfo;
	}

	public void setStatusInfo(String statusInfo) {
		this.statusInfo = statusInfo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
