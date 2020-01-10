package com.jdry.pms.contract.pojo;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "v_contract_attachment")
public class VContractAttachment {

	
	  String  contractId;
	  String  fileId;
	  String  fileName;
	  Date  fileUploadTime;
	  String  filePath;
	
	  @Id
	  @Column(name = "file_id")
	  @GeneratedValue(generator = "system-uuid")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")
	  public String getFileId(){
	  	return this.fileId;
	  }
	  	
	  public void setFileId(String fileId){
		  this.fileId =  fileId;
	  }
	  
	  @Column(name = "file_name")
	  public String getFileName(){
		  return this.fileName;
	  }
	  
	  public void setFileName(String fileName){
		  this.fileName =  fileName;
	  }
	  @Column(name = "file_upload_time")
	  public Date getFileUploadTime(){
		  return this.fileUploadTime;
	  }
	  
	  public void setFileUploadTime(Date fileUploadTime){
		  this.fileUploadTime =  fileUploadTime;
	  }
	  @Column(name = "file_path")
	  public String getFilePath(){
		  return this.filePath;
	  }
	  
	  public void setFilePath(String filePath){
		  this.filePath =  filePath;
	  }
	  
	  @Column(name = "contract_id")
	  public String getContractId() {
		  return contractId;
	  }
	  
	  public void setContractId(String contractId) {
		  this.contractId = contractId;
	  }
	  
}
