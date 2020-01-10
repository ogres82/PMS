package com.jdry.pms.secManage.pojo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class SecurityCalendarEvent {
	private String id;
	private String title;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date start;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	
}
