package com.jdry.pms.assignWork.pojo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserDeptEntitiy implements Serializable
{
	private String userName;
	private String cname;
	private String dept_id;
	private String dept_name;
	private String deption_id;
	private String deption_name;
	private String work_state;
	private String work_times;
	public String getUserName() {
		return userName;
	}
	public String getCname() {
		return cname;
	}
	public String getDept_id() {
		return dept_id;
	}
	public String getDept_name() {
		return dept_name;
	}
	public String getDeption_id() {
		return deption_id;
	}
	public String getDeption_name() {
		return deption_name;
	}
	public String getWork_state() {
		return work_state;
	}
	public String getWork_times() {
		return work_times;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public void setDeption_id(String deption_id) {
		this.deption_id = deption_id;
	}
	public void setDeption_name(String deption_name) {
		this.deption_name = deption_name;
	}
	public void setWork_state(String work_state) {
		this.work_state = work_state;
	}
	public void setWork_times(String work_times) {
		this.work_times = work_times;
	}

}
