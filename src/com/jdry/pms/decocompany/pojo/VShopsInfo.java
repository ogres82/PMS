package com.jdry.pms.decocompany.pojo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "v_t_shops_info")
public class VShopsInfo {

	
	
	  String  id;
	
	  public void setId(String id){
	  	this.id =  id;
	  }
	  
		  String  name;
		  String  summary;
		  String  telephone;
		  String  logoUrl;
		  String  editorId;
		  String  editorName;
		  String  logoPath;
		  String  checker;
		  String  consultor;
	  
	  @Id
	  @Column(name = "id")
	  public String getId(){
	  	return this.id;
	  }
	  	
	  
		  @Column(name = "name")
		  public String getName(){
		  	return this.name;
		  }
		  	
		  public void setName(String name){
		  	this.name =  name;
		  }
		  @Column(name = "summary")
		  public String getSummary(){
		  	return this.summary;
		  }
		  	
		  public void setSummary(String summary){
		  	this.summary =  summary;
		  }
		  @Column(name = "telephone")
		  public String getTelephone(){
		  	return this.telephone;
		  }
		  	
		  public void setTelephone(String telephone){
		  	this.telephone =  telephone;
		  }
		  @Column(name = "logo_url")
		  public String getLogoUrl(){
		  	return this.logoUrl;
		  }
		  	
		  public void setLogoUrl(String logoUrl){
		  	this.logoUrl =  logoUrl;
		  }
		  @Column(name = "editor_id")
		  public String getEditorId(){
		  	return this.editorId;
		  }
		  	
		  public void setEditorId(String editorId){
		  	this.editorId =  editorId;
		  }
		  @Column(name = "editor_name")
		  public String getEditorName(){
		  	return this.editorName;
		  }
		  	
		  public void setEditorName(String editorName){
		  	this.editorName =  editorName;
		  }
		  
		  @Column(name = "logo_path")
		  public String getLogoPath(){
		  	return this.logoPath;
		  }
		  	
		  public void setLogoPath(String logoPath){
		  	this.logoPath =  logoPath;
		  }
		  
		  @Column(name = "checker")
		  public String getChecker() {
			  return checker;
		  }
		  
		  
		  public void setChecker(String checker) {
			  this.checker = checker;
		  }
		  
		  @Column(name = "consultor")
		  public String getConsultor() {
			  return consultor;
		  }
		  
		  
		  public void setConsultor(String consultor) {
			  this.consultor = consultor;
		  }
}
