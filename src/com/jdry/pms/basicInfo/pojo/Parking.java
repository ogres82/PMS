package com.jdry.pms.basicInfo.pojo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "t_parking")
public class Parking {

	
	
	  String  carportId;
	
	  public void setCarportId(String carportId){
	  	this.carportId =  carportId;
	  }
	  
		  String  belongComId;
		  String  belongParkNo;
		  String  carportNo;
		  String  carportStatus;
		  String  carportFloor;
		  String  carportType;
		  String  licensePlateNo;
		  String  remark;
	  
	  
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

		  @Column(name = "carport_floor")
		  public String getCarportFloor() {
			return carportFloor;
		  }


		public void setCarportFloor(String carportFloor) {
			this.carportFloor = carportFloor;
		}

		@Column(name = "carport_type")
		public String getCarportType() {
			return carportType;
		}


		public void setCarportType(String carportType) {
			this.carportType = carportType;
		}
		  
}
