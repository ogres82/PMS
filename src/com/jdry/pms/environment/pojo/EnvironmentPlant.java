package com.jdry.pms.environment.pojo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "t_environment_plant")
public class EnvironmentPlant {

	
	
	  String  plantId;
	
	  public void setPlantId(String plantId){
	  	this.plantId =  plantId;
	  }
	  
		  String  plantName;
		  String  plantMtnStandard;
		  String  plantType;
		  String  remark;
	  
	  
	  @Id
	  @Column(name = "plant_id")
	  @GeneratedValue(generator = "system-uuid")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")
	  public String getPlantId(){
	  	return this.plantId;
	  }
	  	
	  
		  @Column(name = "plant_name")
		  public String getPlantName(){
		  	return this.plantName;
		  }
		  	
		  public void setPlantName(String plantName){
		  	this.plantName =  plantName;
		  }
		  @Column(name = "plant_mtn_standard")
		  public String getPlantMtnStandard(){
		  	return this.plantMtnStandard;
		  }
		  	
		  public void setPlantMtnStandard(String plantMtnStandard){
		  	this.plantMtnStandard =  plantMtnStandard;
		  }
		  @Column(name = "plant_type")
		  public String getPlantType(){
		  	return this.plantType;
		  }
		  	
		  public void setPlantType(String plantType){
		  	this.plantType =  plantType;
		  }
		  @Column(name = "remark")
		  public String getRemark(){
		  	return this.remark;
		  }
		  	
		  public void setRemark(String remark){
		  	this.remark =  remark;
		  }
}
