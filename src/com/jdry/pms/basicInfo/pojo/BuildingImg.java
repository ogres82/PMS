package com.jdry.pms.basicInfo.pojo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "t_building_img")
public class BuildingImg {

	
	
	  String  imgId;
	
	  public void setImgId(String imgId){
	  	this.imgId =  imgId;
	  }
	  
		  String  imgName;
		  String  imgPath;
		  String  imgUrl;
		  String  typeId;
	  
	  
	  @Id
	  @Column(name = "img_id")
	  public String getImgId(){
	  	return this.imgId;
	  }
	  	
	  
		  @Column(name = "img_name")
		  public String getImgName(){
		  	return this.imgName;
		  }
		  	
		  public void setImgName(String imgName){
		  	this.imgName =  imgName;
		  }
		  @Column(name = "img_path")
		  public String getImgPath(){
		  	return this.imgPath;
		  }
		  	
		  public void setImgPath(String imgPath){
		  	this.imgPath =  imgPath;
		  }
		  @Column(name = "img_url")
		  public String getImgUrl(){
		  	return this.imgUrl;
		  }
		  	
		  public void setImgUrl(String imgUrl){
		  	this.imgUrl =  imgUrl;
		  }
		  @Column(name = "type_id")
		  public String getTypeId(){
		  	return this.typeId;
		  }
		  	
		  public void setTypeId(String typeId){
		  	this.typeId =  typeId;
		  }
}
