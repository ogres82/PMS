package com.jdry.pms.system.pojo;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "system_log")
public class SystemLog {
	
	  String  id;
	  String  userName;
	  String  userCname;
	  String  userPhone;
	  String  userPos;
	  String  userDept;
	  String  userOperation;
	  Date  createTime;
	  
	  @Id
	  @Column(name = "id")
	  @GeneratedValue(generator = "system-uuid")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")
	  public String getId(){
	  	return this.id;
	  }
	  	
	  public void setId(String id){
		  this.id =  id;
	  }
	  
	  @Column(name = "user_name")
	  public String getUserName(){
		  return this.userName;
	  }
	  
	  public void setUserName(String userName){
		  this.userName =  userName;
	  }
	  
	  @Column(name = "user_cname")
	  public String getUserCname() {
		  return userCname;
	  }
	  
	  public void setUserCname(String userCname) {
		  this.userCname = userCname;
	  }

	  @Column(name = "user_phone")
	  public String getUserPhone() {
		  return userPhone;
	  }
	  
	  public void setUserPhone(String userPhone) {
		  this.userPhone = userPhone;
	  }

	  @Column(name = "user_pos")
	  public String getUserPos(){
		  return this.userPos;
	  }
	  
	  public void setUserPos(String userPos){
		  this.userPos =  userPos;
	  }
	  @Column(name = "user_dept")
	  public String getUserDept(){
		  return this.userDept;
	  }
	  
	  public void setUserDept(String userDept){
		  this.userDept =  userDept;
	  }
	  @Column(name = "user_operation")
	  public String getUserOperation(){
		  return this.userOperation;
	  }
	  
	  public void setUserOperation(String userOperation){
		  this.userOperation =  userOperation;
	  }
	  @Column(name = "create_time")
	  public Date getCreateTime(){
		  return this.createTime;
	  }
	  
	  public void setCreateTime(Date createTime){
		  this.createTime =  createTime;
	  }
}
