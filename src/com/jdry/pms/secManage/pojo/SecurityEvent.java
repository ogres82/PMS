package com.jdry.pms.secManage.pojo;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

import com.alibaba.fastjson.annotation.JSONField;


@Entity
@Table(name = "t_security_event")
public class SecurityEvent {

	  String  eventId;
	
	  public void setEventId(String eventId){
	  	this.eventId =  eventId;
	  }
	  
		  String  eventCode;
		  String  eventTitle;
		  String  eventType;
		  String  eventBurst;
		  @JSONField (format="yyyy-MM-dd HH:mm:ss") 
		  Date  eventHappenTime;
		  String  eventDetail;
		  String  eventResult;
		  String  eventChargerId;
		  @JSONField (format="yyyy-MM-dd HH:mm:ss") 
		  Date  eventRecTime;
		  String  eventRemark;
	  
	  
	  @Id
	  @Column(name = "event_id")
	  @GeneratedValue(generator = "system-uuid")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")
	  public String getEventId(){
	  	return this.eventId;
	  }
	  	
	  
		  @Column(name = "event_code")
		  public String getEventCode(){
		  	return this.eventCode;
		  }
		  	
		  public void setEventCode(String eventCode){
		  	this.eventCode =  eventCode;
		  }
		  @Column(name = "event_title")
		  public String getEventTitle(){
		  	return this.eventTitle;
		  }
		  	
		  public void setEventTitle(String eventTitle){
		  	this.eventTitle =  eventTitle;
		  }
		  @Column(name = "event_type")
		  public String getEventType(){
		  	return this.eventType;
		  }
		  	
		  public void setEventType(String eventType){
		  	this.eventType =  eventType;
		  }
		  @Column(name = "event_burst")
		  public String getEventBurst(){
		  	return this.eventBurst;
		  }
		  	
		  public void setEventBurst(String eventBurst){
		  	this.eventBurst =  eventBurst;
		  }
		  @Column(name = "event_happen_time")
		  @Temporal(TemporalType.TIMESTAMP)
		  public Date getEventHappenTime(){
		  	return this.eventHappenTime;
		  }
		  	
		  public void setEventHappenTime(Date eventHappenTime){
		  	this.eventHappenTime =  eventHappenTime;
		  }
		  @Column(name = "event_detail")
		  public String getEventDetail(){
		  	return this.eventDetail;
		  }
		  	
		  public void setEventDetail(String eventDetail){
		  	this.eventDetail =  eventDetail;
		  }
		  @Column(name = "event_result")
		  public String getEventResult(){
		  	return this.eventResult;
		  }
		  	
		  public void setEventResult(String eventResult){
		  	this.eventResult =  eventResult;
		  }
		  @Column(name = "event_charger_id")
		  public String getEventChargerId(){
		  	return this.eventChargerId;
		  }
		  	
		  public void setEventChargerId(String eventChargerId){
		  	this.eventChargerId =  eventChargerId;
		  }
		  @Column(name = "event_rec_time")
		  @Temporal(TemporalType.TIMESTAMP)
		  public Date getEventRecTime(){
		  	return this.eventRecTime;
		  }
		  	
		  public void setEventRecTime(Date eventRecTime){
		  	this.eventRecTime =  eventRecTime;
		  }
		  @Column(name = "event_remark")
		  public String getEventRemark(){
		  	return this.eventRemark;
		  }
		  	
		  public void setEventRemark(String eventRemark){
		  	this.eventRemark =  eventRemark;
		  }
}
