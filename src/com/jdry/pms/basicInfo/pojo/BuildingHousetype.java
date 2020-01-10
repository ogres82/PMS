package com.jdry.pms.basicInfo.pojo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "t_building_housetype")
public class BuildingHousetype {

	
	
	  String  typeId;
	
	  public void setTypeId(String typeId){
	  	this.typeId =  typeId;
	  }
	  
		  String  typeName;
		  String  remark;
	  
	  
	  @Id
	  @Column(name = "type_id")
	  @GeneratedValue(generator = "system-uuid")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")
	  public String getTypeId(){
	  	return this.typeId;
	  }
	  	
	  
		  @Column(name = "type_name")
		  public String getTypeName(){
		  	return this.typeName;
		  }
		  	
		  public void setTypeName(String typeName){
		  	this.typeName =  typeName;
		  }
		  @Column(name = "remark")
		  public String getRemark(){
		  	return this.remark;
		  }
		  	
		  public void setRemark(String remark){
		  	this.remark =  remark;
		  }
}
