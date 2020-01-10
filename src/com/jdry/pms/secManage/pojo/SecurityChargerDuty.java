package com.jdry.pms.secManage.pojo;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import com.alibaba.fastjson.annotation.JSONField;


@Entity
@Table(name = "t_security_charger_duty")
public class SecurityChargerDuty {

	
	
	  String  recId;
	
	  public void setRecId(String recId){
	  	this.recId =  recId;
	  }
	  
		  String  recCode;
		  String  shiftType;
		  @JSONField (format="yyyy-MM-dd HH:mm:ss") 
		  Date  shiftDate;
		  String  shiftPasserId;
		  Integer  shiftDutyNum;
		  String  shiftDutyList;
		  String  shiftDutyVocate;
		  String  shiftDutyPerform;
		  String  shiftGoodsTransfer;
		  String  shiftTakeCase;
		  String  shiftDutyCase;
		  String  shiftPassCase;
		  String  shiftMarker;
		  String  shiftTakerId;
		  @JSONField (format="yyyy-MM-dd HH:mm:ss") 
		  Date  shiftTakeTime;
		  String  shiftCheckMarker;
		  String  shiftCheckerId;
		  @JSONField (format="yyyy-MM-dd HH:mm:ss") 
		  Date  shiftCheckTime;
	  
	  
	  @Id
	  @Column(name = "rec_id")
	  @GeneratedValue(generator = "system-uuid")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")
	  public String getRecId(){
	  	return this.recId;
	  }
	  	
	  
		  @Column(name = "rec_code")
		  public String getRecCode(){
		  	return this.recCode;
		  }
		  	
		  public void setRecCode(String recCode){
		  	this.recCode =  recCode;
		  }
		  @Column(name = "shift_type")
		  public String getShiftType(){
		  	return this.shiftType;
		  }
		  	
		  public void setShiftType(String shiftType){
		  	this.shiftType =  shiftType;
		  }
		  @Column(name = "shift_date")
		  public Date getShiftDate(){
		  	return this.shiftDate;
		  }
		  	
		  public void setShiftDate(Date shiftDate){
		  	this.shiftDate =  shiftDate;
		  }
		  @Column(name = "shift_passer_id")
		  public String getShiftPasserId(){
		  	return this.shiftPasserId;
		  }
		  	
		  public void setShiftPasserId(String shiftPasserId){
		  	this.shiftPasserId =  shiftPasserId;
		  }
		  @Column(name = "shift_duty_num")
		  public Integer getShiftDutyNum(){
		  	return this.shiftDutyNum;
		  }
		  	
		  public void setShiftDutyNum(Integer shiftDutyNum){
		  	this.shiftDutyNum =  shiftDutyNum;
		  }
		  @Column(name = "shift_duty_list")
		  public String getShiftDutyList(){
		  	return this.shiftDutyList;
		  }
		  	
		  public void setShiftDutyList(String shiftDutyList){
		  	this.shiftDutyList =  shiftDutyList;
		  }
		  @Column(name = "shift_duty_vocate")
		  public String getShiftDutyVocate(){
		  	return this.shiftDutyVocate;
		  }
		  	
		  public void setShiftDutyVocate(String shiftDutyVocate){
		  	this.shiftDutyVocate =  shiftDutyVocate;
		  }
		  @Column(name = "shift_duty_perform")
		  public String getShiftDutyPerform(){
		  	return this.shiftDutyPerform;
		  }
		  	
		  public void setShiftDutyPerform(String shiftDutyPerform){
		  	this.shiftDutyPerform =  shiftDutyPerform;
		  }
		  @Column(name = "shift_goods_transfer")
		  public String getShiftGoodsTransfer(){
		  	return this.shiftGoodsTransfer;
		  }
		  	
		  public void setShiftGoodsTransfer(String shiftGoodsTransfer){
		  	this.shiftGoodsTransfer =  shiftGoodsTransfer;
		  }
		  @Column(name = "shift_take_case")
		  public String getShiftTakeCase(){
		  	return this.shiftTakeCase;
		  }
		  	
		  public void setShiftTakeCase(String shiftTakeCase){
		  	this.shiftTakeCase =  shiftTakeCase;
		  }
		  @Column(name = "shift_duty_case")
		  public String getShiftDutyCase(){
		  	return this.shiftDutyCase;
		  }
		  	
		  public void setShiftDutyCase(String shiftDutyCase){
		  	this.shiftDutyCase =  shiftDutyCase;
		  }
		  @Column(name = "shift_pass_case")
		  public String getShiftPassCase(){
		  	return this.shiftPassCase;
		  }
		  	
		  public void setShiftPassCase(String shiftPassCase){
		  	this.shiftPassCase =  shiftPassCase;
		  }
		  @Column(name = "shift_marker")
		  public String getShiftMarker(){
		  	return this.shiftMarker;
		  }
		  	
		  public void setShiftMarker(String shiftMarker){
		  	this.shiftMarker =  shiftMarker;
		  }
		  @Column(name = "shift_taker_id")
		  public String getShiftTakerId(){
		  	return this.shiftTakerId;
		  }
		  	
		  public void setShiftTakerId(String shiftTakerId){
		  	this.shiftTakerId =  shiftTakerId;
		  }
		  @Column(name = "shift_take_time")
		  public Date getShiftTakeTime(){
		  	return this.shiftTakeTime;
		  }
		  	
		  public void setShiftTakeTime(Date shiftTakeTime){
		  	this.shiftTakeTime =  shiftTakeTime;
		  }
		  @Column(name = "shift_check_marker")
		  public String getShiftCheckMarker(){
		  	return this.shiftCheckMarker;
		  }
		  	
		  public void setShiftCheckMarker(String shiftCheckMarker){
		  	this.shiftCheckMarker =  shiftCheckMarker;
		  }
		  @Column(name = "shift_checker_id")
		  public String getShiftCheckerId(){
		  	return this.shiftCheckerId;
		  }
		  	
		  public void setShiftCheckerId(String shiftCheckerId){
		  	this.shiftCheckerId =  shiftCheckerId;
		  }
		  @Column(name = "shift_check_time")
		  public Date getShiftCheckTime(){
		  	return this.shiftCheckTime;
		  }
		  	
		  public void setShiftCheckTime(Date shiftCheckTime){
		  	this.shiftCheckTime =  shiftCheckTime;
		  }
}
