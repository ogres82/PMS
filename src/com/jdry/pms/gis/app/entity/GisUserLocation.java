package com.jdry.pms.gis.app.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "t_gis_user_location")
public class GisUserLocation implements Serializable{

	private static final long serialVersionUID = 1L;
	String  id;
	  String  username;
	  String  cname;
	  String  xAxis;
	  String  yAxis;
	  long  realtime;
	  
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
	  
	  @Column(name = "username")
	  public String getUsername(){
		  return this.username;
	  }
	  
	  public void setUsername(String username){
		  this.username =  username;
	  }
	  @Column(name = "cname")
	  public String getCname(){
		  return this.cname;
	  }
	  
	  public void setCname(String cname){
		  this.cname =  cname;
	  }
	  @Column(name = "x_axis")
	  public String getXAxis(){
		  return this.xAxis;
	  }
	  
	  public void setXAxis(String xAxis){
		  this.xAxis =  xAxis;
	  }
	  @Column(name = "y_axis")
	  public String getYAxis(){
		  return this.yAxis;
	  }
	  
	  public void setYAxis(String yAxis){
		  this.yAxis =  yAxis;
	  }
	  @Column(name = "realtime")
	  public long getRealtime(){
		  return this.realtime;
	  }
	  
	  public void setRealtime(long realtime){
		  this.realtime =  realtime;
	  }
}
