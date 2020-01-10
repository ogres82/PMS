package com.jdry.pms.environment.pojo;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "t_environment_cleaning_area_duty")
public class EnvironmentCleaningAreaDuty {

	
	
	  String  planId;
	
	  public void setPlanId(String planId){
	  	this.planId =  planId;
	  }
	  
		  String  planCode;
		  Date  cleanBeginDate;
		  Date  cleanEndDate;
		  String  cleaningType;
		  String  cleaningArea;
		  String  cleaningDetail;
		  String  cleaningPerson;
	  
	  
	  @Id
	  @Column(name = "plan_id")
	  @GeneratedValue(generator = "system-uuid")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")
	  public String getPlanId(){
	  	return this.planId;
	  }
	  	
	  
		  @Column(name = "plan_code")
		  public String getPlanCode(){
		  	return this.planCode;
		  }
		  	
		  public void setPlanCode(String planCode){
		  	this.planCode =  planCode;
		  }
		  @Column(name = "clean_begin_date")
		  public Date getCleanBeginDate(){
		  	return this.cleanBeginDate;
		  }
		  	
		  public void setCleanBeginDate(Date cleanBeginDate){
		  	this.cleanBeginDate =  cleanBeginDate;
		  }
		  @Column(name = "clean_end_date")
		  public Date getCleanEndDate(){
		  	return this.cleanEndDate;
		  }
		  	
		  public void setCleanEndDate(Date cleanEndDate){
		  	this.cleanEndDate =  cleanEndDate;
		  }
		  @Column(name = "cleaning_type")
		  public String getCleaningType(){
		  	return this.cleaningType;
		  }
		  	
		  public void setCleaningType(String cleaningType){
		  	this.cleaningType =  cleaningType;
		  }
		  @Column(name = "cleaning_area")
		  public String getCleaningArea(){
		  	return this.cleaningArea;
		  }
		  	
		  public void setCleaningArea(String cleaningArea){
		  	this.cleaningArea =  cleaningArea;
		  }
		  @Column(name = "cleaning_detail")
		  public String getCleaningDetail(){
		  	return this.cleaningDetail;
		  }
		  	
		  public void setCleaningDetail(String cleaningDetail){
		  	this.cleaningDetail =  cleaningDetail;
		  }
		  @Column(name = "cleaning_person")
		  public String getCleaningPerson(){
		  	return this.cleaningPerson;
		  }
		  	
		  public void setCleaningPerson(String cleaningPerson){
		  	this.cleaningPerson =  cleaningPerson;
		  }
}
