package com.jdry.pms.environment.pojo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "v_area_plant")
public class VAreaPlant {

	
	
	  String  areaId;
	  String  areaName;
	  String  areaResPerson;
	  String  areaResPersonName;
	  String  areaGisId;
	  String  areaGisArea;
	  String  remark;
	
	  
	  
	  @Id
	  @Column(name = "area_id")
	  @GeneratedValue(generator = "system-uuid")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")
	  public String getAreaId(){
	  	return this.areaId;
	  }
	  	
	  public void setAreaId(String areaId){
		  this.areaId =  areaId;
	  }
	  
	  @Column(name = "area_name")
	  public String getAreaName(){
	  	return this.areaName;
	  }
	  	
	  public void setAreaName(String areaName){
	  	this.areaName =  areaName;
	  }
	  @Column(name = "area_res_person")
	  public String getAreaResPerson(){
	  	return this.areaResPerson;
	  }
	  	
	  public void setAreaResPerson(String areaResPerson){
	  	this.areaResPerson =  areaResPerson;
	  }
	  @Column(name = "remark")
	  public String getRemark(){
	  	return this.remark;
	  }
	  	
	  public void setRemark(String remark){
	  	this.remark =  remark;
	  }
	  @Column(name = "area_res_person_name")
	  public String getAreaResPersonName() {
		  return areaResPersonName;
	  }
	  
	  public void setAreaResPersonName(String areaResPersonName) {
		  this.areaResPersonName = areaResPersonName;
	  }
	  
	  @Column(name = "area_gis_area")
	  public String getAreaGisArea() {
		  return areaGisArea;
	  }
	  
	  public void setAreaGisArea(String areaGisArea) {
		  this.areaGisArea = areaGisArea;
	  }

	  @Column(name = "area_gis_id")
	  public String getAreaGisId() {
		  return areaGisId;
	  }
	  
	  
	  public void setAreaGisId(String areaGisId) {
		  this.areaGisId = areaGisId;
	  }

}
