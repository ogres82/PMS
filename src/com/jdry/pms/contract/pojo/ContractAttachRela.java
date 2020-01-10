package com.jdry.pms.contract.pojo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "t_contract_attach_rela")
public class ContractAttachRela {

	
	
	  String  id;
	  String  fileId;
	  String  contractId;
	  
	  @Id
	  @Column(name = "id")
	  @GeneratedValue(generator = "system-uuid")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")
	  public String getId(){
	  	return this.id;
	  }
	  	
	  public void setId(String id){
		  this.id =  id;
	  }
	  
	  @Column(name = "file_id")
	  public String getFileId(){
		  return this.fileId;
	  }
	  
	  public void setFileId(String fileId){
		  this.fileId =  fileId;
	  }
	  @Column(name = "contract_id")
	  public String getContractId(){
		  return this.contractId;
	  }
	  
	  public void setContractId(String contractId){
		  this.contractId =  contractId;
	  }
}
