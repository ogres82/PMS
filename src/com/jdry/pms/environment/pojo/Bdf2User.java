package com.jdry.pms.environment.pojo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "v_user_dept_pos")
public class Bdf2User {

	
	
	  String  username;
	  String  cname;
	  String  deptId;
	  String  deptName;
	  String  positionId;
	  String  positionName;
	  String  workState;
	  String  workTimes;
	  
	  @Id
	  @Column(name = "username_")
	  @GeneratedValue(generator = "system-uuid")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")
	  public String getUsername(){
	  	return this.username;
	  }
	  	
	  public void setUsername(String username){
		  this.username =  username;
	  }

	  @Column(name = "cname_")
	  public String getCname() {
		  return cname;
	  }
	  
	  public void setCname(String cname) {
		  this.cname = cname;
	  }
	  @Column(name = "dept_id")
	  public String getDeptId() {
		  return deptId;
	  }
	  
	  public void setDeptId(String deptId) {
		  this.deptId = deptId;
	  }
	  @Column(name = "dept_name")
	  public String getDeptName() {
		  return deptName;
	  }
	  
	  public void setDeptName(String deptName) {
		  this.deptName = deptName;
	  }
	  @Column(name = "position_id")
	  public String getPositionId() {
		  return positionId;
	  }
	  
	  public void setPositionId(String positionId) {
		  this.positionId = positionId;
	  }
	  @Column(name = "position")
	  public String getPositionName() {
		  return positionName;
	  }
	  
	  public void setPositionName(String positionName) {
		  this.positionName = positionName;
	  }
	  @Column(name = "work_state")
	  public String getWorkState() {
		  return workState;
	  }
	  
	  public void setWorkState(String workState) {
		  this.workState = workState;
	  }
	  @Column(name = "work_times")
	  public String getWorkTimes() {
		  return workTimes;
	  }
	  
	  public void setWorkTimes(String workTimes) {
		  this.workTimes = workTimes;
	  }
	  
	  
}
