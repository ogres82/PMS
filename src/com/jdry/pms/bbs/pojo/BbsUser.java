package com.jdry.pms.bbs.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_bbs_user")
public class BbsUser {

	private String userId;
	private String userNickname;
	private String userType;
	private String mappingUserId;
	
	
	@Column(name = "user_id")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(name = "user_nickname")
	public String getUserNickname() {
		return userNickname;
	}
	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}
	@Column(name = "user_type")
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	@Id
	@Column(name = "mapping_user_id")
	public String getMappingUserId() {
		return mappingUserId;
	}
	public void setMappingUserId(String mappingUserId) {
		this.mappingUserId = mappingUserId;
	}
	
	
}
