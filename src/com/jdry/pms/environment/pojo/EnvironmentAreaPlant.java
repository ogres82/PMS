package com.jdry.pms.environment.pojo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "t_environment_area_plant")
public class EnvironmentAreaPlant {
	
	  String  id;
	  String  areaId;
	  String  plantId;
	
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
	  
	  @Column(name = "area_id")
	  public String getAreaId(){
		  return this.areaId;
	  }
	  
	  public void setAreaId(String areaId){
		  this.areaId =  areaId;
	  }
	  @Column(name = "plant_id")
	  public String getPlantId(){
		  return this.plantId;
	  }
	  
	  public void setPlantId(String plantId){
		  this.plantId =  plantId;
	  }
}
