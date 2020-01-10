package com.jdry.pms.environment.pojo;


import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "t_environment_area")
public class EnvironmentArea {

	
	
	  String  areaId;
	  String  areaName;
	  String  areaResPerson;
	  String  areaGisId;
	  BigDecimal  areaGisArea;
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
	  
	  @Column(name = "area_gis_id")
	  public String getAreaGisId() {
		  return areaGisId;
	  }
	  
	  
	  public void setAreaGisId(String areaGisId) {
		  this.areaGisId = areaGisId;
	  }
	  
	  @Column(name = "area_gis_area")
	  public BigDecimal getAreaGisArea() {
		  return areaGisArea;
	  }
	  
	  public void setAreaGisArea(BigDecimal areaGisArea) {
		  this.areaGisArea = areaGisArea;
	  }
}
