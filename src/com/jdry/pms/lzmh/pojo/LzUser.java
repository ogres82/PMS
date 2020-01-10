package com.jdry.pms.lzmh.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "t_lzmh_user")
public class LzUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer userId;
	private String validityStartDate;
	private String validityEndDate;
	private String validityStartTime;
	private String validityEndTime;
	private String state;
	private String identityName;
	
	
	
	@Id
	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "validity_start_date")
	public String getValidityStartDate() {
		return validityStartDate;
	}

	public void setValidityStartDate(String validityStartDate) {
		this.validityStartDate = validityStartDate;
	}

	@Column(name = "validity_end_date")
	public String getValidityEndDate() {
		return validityEndDate;
	}

	public void setValidityEndDate(String validityEndDate) {
		this.validityEndDate = validityEndDate;
	}

	@Column(name = "validity_start_time")
	public String getValidityStartTime() {
		return validityStartTime;
	}

	public void setValidityStartTime(String validityStartTime) {
		this.validityStartTime = validityStartTime;
	}

	@Column(name = "validity_end_time")
	public String getValidityEndTime() {
		return validityEndTime;
	}

	public void setValidityEndTime(String validityEndTime) {
		this.validityEndTime = validityEndTime;
	}

	@Column(name = "state")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "identity_name")
	public String getIdentityName() {
		return identityName;
	}

	public void setIdentityName(String identityName) {
		this.identityName = identityName;
	}
}
