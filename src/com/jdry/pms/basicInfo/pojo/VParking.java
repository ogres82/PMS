package com.jdry.pms.basicInfo.pojo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "v_t_parking")
public class VParking {

	
	
	  String  carportId;
	
	  public void setCarportId(String carportId){
	  	this.carportId =  carportId;
	  }
	  
		  String  belongComId;
		  String  belongParkNo;
		  String  carportNo;
		  String  carportStatus;
		  String  roomNo;
		  String  ownerId;
		  String  ownerName;
		  String  ownerPhone;
		  String  licensePlateNo;
		  String  carportType;
		  String  build_name;
		  String  carportFloor;
		  String  community_name;
		  String  park_name;
		  String  remark;
		  String  carport_status_name;
		  String  build_id;
		  String  community_id;
	  
	  @Id
	  @Column(name = "carport_id")
	  @GeneratedValue(generator = "system-uuid")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")
	  public String getCarportId(){
	  	return this.carportId;
	  }
	  	
	  
		  @Column(name = "belong_com_id")
		  public String getBelongComId(){
		  	return this.belongComId;
		  }
		  	
		  public void setBelongComId(String belongComId){
		  	this.belongComId =  belongComId;
		  }
		  @Column(name = "belong_park_no")
		  public String getBelongParkNo(){
		  	return this.belongParkNo;
		  }
		  	
		  public void setBelongParkNo(String belongParkNo){
		  	this.belongParkNo =  belongParkNo;
		  }
		  @Column(name = "carport_no")
		  public String getCarportNo(){
		  	return this.carportNo;
		  }
		  	
		  public void setCarportNo(String carportNo){
		  	this.carportNo =  carportNo;
		  }
		  @Column(name = "carport_status")
		  public String getCarportStatus(){
		  	return this.carportStatus;
		  }
		  	
		  public void setCarportStatus(String carportStatus){
		  	this.carportStatus =  carportStatus;
		  }
		  @Column(name = "room_no")
		  public String getRoomNo(){
		  	return this.roomNo;
		  }
		  	
		  public void setRoomNo(String roomNo){
		  	this.roomNo =  roomNo;
		  }
		  @Column(name = "owner_name")
		  public String getOwnerName(){
		  	return this.ownerName;
		  }
		  	
		  public void setOwnerName(String ownerName){
		  	this.ownerName =  ownerName;
		  }
		  @Column(name = "phone")
		  public String getOwnerPhone(){
		  	return this.ownerPhone;
		  }
		  	
		  public void setOwnerPhone(String ownerPhone){
		  	this.ownerPhone =  ownerPhone;
		  }
		  @Column(name = "license_plate_no")
		  public String getLicensePlateNo(){
		  	return this.licensePlateNo;
		  }
		  	
		  public void setLicensePlateNo(String licensePlateNo){
		  	this.licensePlateNo =  licensePlateNo;
		  }
		  @Column(name = "remark")
		  public String getRemark(){
		  	return this.remark;
		  }
		  	
		  public void setRemark(String remark){
		  	this.remark =  remark;
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
		  
		  @Column(name = "park_name")
		  public String getPark_name() {
			  return park_name;
		  }
		  
		  
		  public void setPark_name(String park_name) {
			  this.park_name = park_name;
		  }

		@Column(name = "owner_id")
		public String getOwnerId() {
			return ownerId;
		}


		public void setOwnerId(String ownerId) {
			this.ownerId = ownerId;
		}

		@Column(name = "carport_type")
		public String getCarportType() {
			return carportType;
		}


		public void setCarportType(String carportType) {
			this.carportType = carportType;
		}

		@Column(name = "carport_floor")
		public String getCarportFloor() {
			return carportFloor;
		}


		public void setCarportFloor(String carportFloor) {
			this.carportFloor = carportFloor;
		}

		@Column(name = "carport_status_name")
		public String getCarport_status_name() {
			return carport_status_name;
		}


		public void setCarport_status_name(String carport_status_name) {
			this.carport_status_name = carport_status_name;
		}

		@Column(name = "build_id")
		public String getBuild_id() {
			return build_id;
		}


		public void setBuild_id(String build_id) {
			this.build_id = build_id;
		}

		@Column(name = "community_id")
		public String getCommunity_id() {
			return community_id;
		}


		public void setCommunity_id(String community_id) {
			this.community_id = community_id;
		}
		  
		  

}
