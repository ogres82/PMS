package com.jdry.pms.msgpublic.pojo;

import java.util.List;

public class AppMsgpubMain {
	private String msgSubject;
	
	private String dept;
	
	private List<AppMsgPubAttach> attach;

	public String getMsgSubject() {
		return msgSubject;
	}
	private String pubTime;
	
	private String creator;
	
	
	public String getPubTime() {
		return pubTime;
	}

	public void setPubTime(String pubTime) {
		this.pubTime = pubTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public void setMsgSubject(String msgSubject) {
		this.msgSubject = msgSubject;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public List<AppMsgPubAttach> getAttach() {
		return attach;
	}

	public void setAttach(List<AppMsgPubAttach> attach) {
		this.attach = attach;
	}
	
	
	
	
}
