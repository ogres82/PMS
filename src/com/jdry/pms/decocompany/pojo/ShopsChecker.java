package com.jdry.pms.decocompany.pojo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "t_shops_checker")
public class ShopsChecker {

	
	
	  String  id;
	
	  public void setId(String id){
	  	this.id =  id;
	  }
	  
		  String  companyId;
		  String  ownerId;
	  
	  
	  @Id
	  @Column(name = "id")
	  @GeneratedValue(generator = "system-uuid")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")
	  public String getId(){
	  	return this.id;
	  }
	  	
	  
		  @Column(name = "company_id")
		  public String getCompanyId(){
		  	return this.companyId;
		  }
		  	
		  public void setCompanyId(String companyId){
		  	this.companyId =  companyId;
		  }
		  @Column(name = "owner_id")
		  public String getOwnerId(){
		  	return this.ownerId;
		  }
		  	
		  public void setOwnerId(String ownerId){
		  	this.ownerId =  ownerId;
		  }
}
