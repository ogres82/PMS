package com.jdry.pms.assignWork.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_r_maintain_workerstate")
public class WorkMainWorkerStateEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5207301089736055869L;
	private String user_name;
	private Integer work_state;
	private Integer work_times;
	@Id
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public Integer getWork_state() {
		return work_state;
	}
	public void setWork_state(Integer work_state) {
		this.work_state = work_state;
	}
	public Integer getWork_times() {
		return work_times;
	}
	public void setWork_times(Integer work_times) {
		this.work_times = work_times;
	}
	
	
}
