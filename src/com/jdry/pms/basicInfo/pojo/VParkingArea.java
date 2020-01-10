package com.jdry.pms.basicInfo.pojo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "v_t_parking_area")
public class VParkingArea {

	
	
	  String  parkId;
	
	  public void setParkId(String parkId){
	  	this.parkId =  parkId;
	  }
	  
		  String  belongComId;
		  String  parkName;
		  String  build_id;
		  String  build_name;
		  String  community_name;
		  String  remark;
		  String  parkCode;
		  String  parkPosition;
		  String  parkState;
		  String  usingDate;
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
		  @Column(name = "build_id")
		  public String getBuild_id() {
			  return build_id;
		  }
		  
		  
		  public void setBuild_id(String build_id) {
			  this.build_id = build_id;
		  }
		  
		  @Column(name = "build_name")
		  public String getBuild_name() {
			  return build_name;
		  }
		  
		  
		  public void setBuild_name(String build_name) {
			  this.build_name = build_name;
		  }
		  
		  @Column(name = "community_name")
		  public String getCommunity_name() {
			  return community_name;
		  }
		  
		  
		  public void setCommunity_name(String community_name) {
			  this.community_name = community_name;
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
			public String getUsingDate() {
				return usingDate;
			}
	
	
			public void setUsingDate(String usingDate) {
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
