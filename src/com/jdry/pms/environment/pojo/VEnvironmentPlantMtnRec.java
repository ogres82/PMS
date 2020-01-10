package com.jdry.pms.environment.pojo;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "v_environment_plant_mtn_rec")
public class VEnvironmentPlantMtnRec {

	
	
	  String  recId;
	
	  public void setRecId(String recId){
	  	this.recId =  recId;
	  }
	  
		  String  areaId;
		  String  areaName;
		  String  plantMtnDetail;
		  String  plantMtnPerson;
		  String  plantMtnPersonName;
		  Date  plantMtnDate;
		  String  remark;
	  
	  
	  @Id
	  @Column(name = "rec_id")
	  @GeneratedValue(generator = "system-uuid")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")
	  public String getRecId(){
	  	return this.recId;
	  }
	  	
	  
		  @Column(name = "area_id")
		  public String getAreaId(){
		  	return this.areaId;
		  }
		  	
		  public void setAreaId(String areaId){
		  	this.areaId =  areaId;
		  }
		  @Column(name = "plant_mtn_detail")
		  public String getPlantMtnDetail(){
		  	return this.plantMtnDetail;
		  }
		  	
		  public void setPlantMtnDetail(String plantMtnDetail){
		  	this.plantMtnDetail =  plantMtnDetail;
		  }
		  @Column(name = "plant_mtn_person")
		  public String getPlantMtnPerson(){
		  	return this.plantMtnPerson;
		  }
		  	
		  public void setPlantMtnPerson(String plantMtnPerson){
		  	this.plantMtnPerson =  plantMtnPerson;
		  }
		  @Column(name = "plant_mtn_date")
		  public Date getPlantMtnDate(){
		  	return this.plantMtnDate;
		  }
		  	
		  public void setPlantMtnDate(Date plantMtnDate){
		  	this.plantMtnDate =  plantMtnDate;
		  }
		  @Column(name = "remark")
		  public String getRemark(){
		  	return this.remark;
		  }
		  	
		  public void setRemark(String remark){
		  	this.remark =  remark;
		  }
		  @Column(name = "area_name")
		  public String getAreaName() {
			  return areaName;
		  }
		  
		  
		  public void setAreaName(String areaName) {
			  this.areaName = areaName;
		  }
		  
		  @Column(name = "plant_mtn_person_name")
		  public String getPlantMtnPersonName() {
			  return plantMtnPersonName;
		  }
		  
		  
		  public void setPlantMtnPersonName(String plantMtnPersonName) {
			  this.plantMtnPersonName = plantMtnPersonName;
		  }

}
