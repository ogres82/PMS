package com.jdry.pms.msgandnotice.pojo;

public class AppResult {
	private String status;
	private String message;
	private String data;
	private String posiId;
	public String getStatus() {
		return status;
	}
	public String getPosiId() {
		return posiId;
	}
	public void setPosiId(String posiId) {
		this.posiId = posiId;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	

}
