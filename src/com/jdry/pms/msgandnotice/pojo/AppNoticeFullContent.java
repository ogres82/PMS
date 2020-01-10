package com.jdry.pms.msgandnotice.pojo;

import java.util.List;

public class AppNoticeFullContent {
	private String title;
	private String content;
	private String deptName;
	private String pubTime;
	private String createTime;
	private String status;
	private List<AppAuditInfo> auditInfos;
	private List<AppPicture> url;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getPubTime() {
		return pubTime;
	}
	public void setPubTime(String pubTime) {
		this.pubTime = pubTime;
	}
	public List<AppAuditInfo> getAuditInfos() {
		return auditInfos;
	}
	public void setAuditInfos(List<AppAuditInfo> auditInfos) {
		this.auditInfos = auditInfos;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<AppPicture> getUrl() {
		return url;
	}
	public void setUrl(List<AppPicture> url) {
		this.url = url;
	}
	
	
	
}
