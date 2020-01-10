package com.jdry.pms.decocompany.pojo;

import java.util.List;



public class DecorationInfoApp {
	  
		  String  id;
		  String  name;
		  String  summary;
		  String  telephone;
		  String  logoUrl;
		  String  editorId;
		  String  editorName;
		  List<ShopsGoods> imgs;
		  List<ShopsGoodsImg> qualify;
	  
		  public void setId(String id){
			  this.id =  id;
		  }
	  	
		  public String getId(){
			  return this.id;
		  }
	  
		  public String getName(){
		  	return this.name;
		  }
		  	
		  public void setName(String name){
		  	this.name =  name;
		  }
		  public String getSummary(){
		  	return this.summary;
		  }
		  	
		  public void setSummary(String summary){
		  	this.summary =  summary;
		  }
		  public String getTelephone(){
		  	return this.telephone;
		  }
		  	
		  public void setTelephone(String telephone){
		  	this.telephone =  telephone;
		  }
		  public String getLogoUrl(){
		  	return this.logoUrl;
		  }
		  	
		  public void setLogoUrl(String logoUrl){
		  	this.logoUrl =  logoUrl;
		  }
		  public String getEditorId(){
		  	return this.editorId;
		  }
		  	
		  public void setEditorId(String editorId){
		  	this.editorId =  editorId;
		  }
		  public String getEditorName(){
		  	return this.editorName;
		  }
		  	
		  public void setEditorName(String editorName){
		  	this.editorName =  editorName;
		  }

		  public List<ShopsGoods> getImgs() {
			  return imgs;
		  }
		  
		  public void setImgs(List<ShopsGoods> imgs) {
			  this.imgs = imgs;
		  }
		  
		  public List<ShopsGoodsImg> getQualify() {
			  return qualify;
		  }
		  
		  public void setQualify(List<ShopsGoodsImg> qualify) {
			  this.qualify = qualify;
		  }
}
