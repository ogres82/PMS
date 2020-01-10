package com.jdry.pms.basicInfo.pojo;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "t_parking_area")
public class ParkingArea {

	
	
	  String  parkId;
	
	  public void setParkId(String parkId){
	  	this.parkId =  parkId;
	  }
	  
		  String  belongComId;
		  String  parkName;
		  String  remark;
		  String  parkCode;
		  String  parkPosition;
		  String  parkState;
		  Date  usingDate;
		  int  floors;
		  int  parkNum;
	  
	  
	  @Id
	  @Column(name = "park_id")
	  @GeneratedValue(generator = "system-uuid")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")
	  public String getParkId(){
	  	return this.parkId;
	  }
	  	
	  
	  @Column(name = "belong_com_id")
	  public String getBelongComId(){
	  	return this.belongComId;
	  }
	  	
	  public void setBelongComId(String belongComId){
	  	this.belongComId =  belongComId;
	  }
	  @Column(name = "park_name")
	  public String getParkName(){
	  	return this.parkName;
	  }
	  	
	  public void setParkName(String parkName){
	  	this.parkName =  parkName;
	  }
	  @Column(name = "remark")
	  public String getRemark(){
	  	return this.remark;
	  }
	  	
	  public void setRemark(String remark){
	  	this.remark =  remark;
	  }

	  @Column(name = "park_code")
	  public String getParkCode() {
		return parkCode;
	  }


	  public void setParkCode(String parkCode) {
		this.parkCode = parkCode;
	  }

	  @Column(name = "park_position")
	  public String getParkPosition() {
		return parkPosition;
	  }


	  public void setParkPosition(String parkPosition) {
		this.parkPosition = parkPosition;
	  }

	  @Column(name = "park_state")
	  public String getParkState() {
		return parkState;
	  }


	  public void setParkState(String parkState) {
		this.parkState = parkState;
	  }

	  @Column(name = "using_date")
	  public Date getUsingDate() {
		return usingDate;
	  }


	  public void setUsingDate(Date usingDate) {
		this.usingDate = usingDate;
	  }

	  @Column(name = "floors")
	  public int getFloors() {
		return floors;
	  }


	  public void setFloors(int floors) {
		this.floors = floors;
	  }

	  @Column(name = "park_num")
	  public int getParkNum() {
		return parkNum;
	  }


	  public void setParkNum(int parkNum) {
		this.parkNum = parkNum;
	  }
		  
}
