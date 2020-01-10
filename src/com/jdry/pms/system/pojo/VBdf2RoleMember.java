package com.jdry.pms.system.pojo;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "v_role_member_user")
public class VBdf2RoleMember {

	
	
	  String  id;
	
	  public void setId(String id){
	  	this.id =  id;
	  }
	  
		  Date  createDate;
		  String  deptId;
		  Boolean  granted;
		  String  positionId;
		  String  roleId;
		  String  username;
		  String  groupId;
		  String  cname;
	  
	  @Id
	  @Column(name = "id_")
	  @GeneratedValue(generator = "system-uuid")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")
	  public String getId(){
	  	return this.id;
	  }
	  	
	  
		  @Column(name = "CREATE_DATE_")
		  public Date getCreateDate(){
		  	return this.createDate;
		  }
		  	
		  public void setCreateDate(Date createDate){
		  	this.createDate =  createDate;
		  }
		  @Column(name = "DEPT_ID_")
		  public String getDeptId(){
		  	return this.deptId;
		  }
		  	
		  public void setDeptId(String deptId){
		  	this.deptId =  deptId;
		  }
		  @Column(name = "GRANTED_")
		  public Boolean getGranted(){
		  	return this.granted;
		  }
		  	
		  public void setGranted(Boolean granted){
		  	this.granted =  granted;
		  }
		  @Column(name = "POSITION_ID_")
		  public String getPositionId(){
		  	return this.positionId;
		  }
		  	
		  public void setPositionId(String positionId){
		  	this.positionId =  positionId;
		  }
		  @Column(name = "ROLE_ID_")
		  public String getRoleId(){
		  	return this.roleId;
		  }
		  	
		  public void setRoleId(String roleId){
		  	this.roleId =  roleId;
		  }
		  @Column(name = "USERNAME_")
		  public String getUsername(){
		  	return this.username;
		  }
		  	
		  public void setUsername(String username){
		  	this.username =  username;
		  }
		  @Column(name = "GROUP_ID_")
		  public String getGroupId(){
		  	return this.groupId;
		  }
		  	
		  public void setGroupId(String groupId){
		  	this.groupId =  groupId;
		  }

		  @Column(name = "CNAME_")
		  public String getCname() {
			  return cname;
		  }
		  
		  
		  public void setCname(String cname) {
			  this.cname = cname;
		  }
		  
}
