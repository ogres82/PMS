package com.jdry.pms.system.pojo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "v_system_btn_url")
public class VSystemBtnUrl {

	
	
	  String  id;
	
	  public void setId(String id){
	  	this.id =  id;
	  }
	  
		  String  urlId;
		  String  urlName;
		  String  btnId;
		  String  btnName;
		  Integer  btnOrder;
		  String pageName;
	  
	  @Id
	  @Column(name = "id")
	  @GeneratedValue(generator = "system-uuid")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")
	  public String getId(){
	  	return this.id;
	  }
	  	
	  
		  @Column(name = "url_id")
		  public String getUrlId(){
		  	return this.urlId;
		  }
		  	
		  public void setUrlId(String urlId){
		  	this.urlId =  urlId;
		  }
		  @Column(name = "url_name")
		  public String getUrlName(){
		  	return this.urlName;
		  }
		  	
		  public void setUrlName(String urlName){
		  	this.urlName =  urlName;
		  }
		  @Column(name = "btn_id")
		  public String getBtnId(){
		  	return this.btnId;
		  }
		  	
		  public void setBtnId(String btnId){
		  	this.btnId =  btnId;
		  }
		  @Column(name = "btn_name")
		  public String getBtnName(){
		  	return this.btnName;
		  }
		  	
		  public void setBtnName(String btnName){
		  	this.btnName =  btnName;
		  }
		 
		  @Column(name = "order_")
		  public Integer getBtnOrder() {
			  return btnOrder;
		  }
		  
		  
		  public void setBtnOrder(Integer btnOrder) {
			  this.btnOrder = btnOrder;
		  }


		@Column(name = "page_name")
		  public String getPageName() {
			  return pageName;
		  }
		  
		  
		  public void setPageName(String pageName) {
			  this.pageName = pageName;
		  }
}
