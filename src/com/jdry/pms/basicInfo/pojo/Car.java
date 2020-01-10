package com.jdry.pms.basicInfo.pojo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "t_car")
public class Car {

	
	
	  String  carId;
	
	  public void setCarId(String carId){
	  	this.carId =  carId;
	  }
	  
		  String  carNo;
		  String  carBrand;
		  String  carModel;
		  String  belongOwnerId;
		  String  remark;
	  
	  
	  @Id
	  @Column(name = "car_id")
	  @GeneratedValue(generator = "system-uuid")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")
	  public String getCarId(){
	  	return this.carId;
	  }
	  	
	  
		  @Column(name = "car_no")
		  public String getCarNo(){
		  	return this.carNo;
		  }
		  	
		  public void setCarNo(String carNo){
		  	this.carNo =  carNo;
		  }
		  @Column(name = "car_brand")
		  public String getCarBrand(){
		  	return this.carBrand;
		  }
		  	
		  public void setCarBrand(String carBrand){
		  	this.carBrand =  carBrand;
		  }
		  @Column(name = "car_model")
		  public String getCarModel(){
		  	return this.carModel;
		  }
		  	
		  public void setCarModel(String carModel){
		  	this.carModel =  carModel;
		  }
		  @Column(name = "belong_owner_id")
		  public String getBelongOwnerId(){
		  	return this.belongOwnerId;
		  }
		  	
		  public void setBelongOwnerId(String belongOwnerId){
		  	this.belongOwnerId =  belongOwnerId;
		  }
		  @Column(name = "remark")
		  public String getRemark(){
		  	return this.remark;
		  }
		  	
		  public void setRemark(String remark){
		  	this.remark =  remark;
		  }
}
