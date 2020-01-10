package com.jdry.pms.environment.pojo;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "v_environment_cleaning_check_rec")
public class VEnvironmentCleaningCheckRec {

	
	
	  String  recId;
	
	  public void setRecId(String recId){
	  	this.recId =  recId;
	  }
	  
		  String  planId;
		  String  planCode;
		  Date  cleaningCheckDate;
		  String  cleaningCheckDetail;
		  String  cleaningCheckRes;
		  String  cleaningCheckResName;
		  String  cleaningCheckerId;
		  String  remark;
		  String  empName;
	  
	  @Id
	  @Column(name = "rec_id")
	  @GeneratedValue(generator = "system-uuid")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")
	  public String getRecId(){
	  	return this.recId;
	  }
	  	
	  
		  @Column(name = "plan_id")
		  public String getPlanId(){
		  	return this.planId;
		  }
		  	
		  public void setPlanId(String planId){
		  	this.planId =  planId;
		  }
		  @Column(name = "cleaning_check_date")
		  public Date getCleaningCheckDate(){
		  	return this.cleaningCheckDate;
		  }
		  	
		  public void setCleaningCheckDate(Date cleaningCheckDate){
		  	this.cleaningCheckDate =  cleaningCheckDate;
		  }
		  @Column(name = "cleaning_check_detail")
		  public String getCleaningCheckDetail(){
		  	return this.cleaningCheckDetail;
		  }
		  	
		  public void setCleaningCheckDetail(String cleaningCheckDetail){
		  	this.cleaningCheckDetail =  cleaningCheckDetail;
		  }
		  @Column(name = "cleaning_check_res")
		  public String getCleaningCheckRes(){
		  	return this.cleaningCheckRes;
		  }
		  	
		  public void setCleaningCheckRes(String cleaningCheckRes){
		  	this.cleaningCheckRes =  cleaningCheckRes;
		  }
		  @Column(name = "cleaning_checker_id")
		  public String getCleaningCheckerId(){
		  	return this.cleaningCheckerId;
		  }
		  	
		  public void setCleaningCheckerId(String cleaningCheckerId){
		  	this.cleaningCheckerId =  cleaningCheckerId;
		  }
		  @Column(name = "remark")
		  public String getRemark(){
		  	return this.remark;
		  }
		  	
		  public void setRemark(String remark){
		  	this.remark =  remark;
		  }
		  @Column(name = "plan_code")
		  public String getPlanCode() {
			  return planCode;
		  }
		  
		  
		  public void setPlanCode(String planCode) {
			  this.planCode = planCode;
		  }
		  
		  @Column(name = "cleaning_check_res_name")
		  public String getCleaningCheckResName() {
			  return cleaningCheckResName;
		  }



		  public void setCleaningCheckResName(String cleaningCheckResName) {
			  this.cleaningCheckResName = cleaningCheckResName;
		  }
		  
		  @Column(name = "emp_name")
		  public String getEmpName() {
			  return empName;
		  }
		  
		  public void setEmpName(String empName) {
			  this.empName = empName;
		  }
		  
		  
}
