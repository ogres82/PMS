package com.jdry.pms.contract.pojo;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "v_contract_rec")
public class VContractRec {

	
	
	  String  recId;
	  String  contractId;
	  String  contractName;
	  String  contractCode;
	  String  contractDealContent;
	  Date  recTime;
	  String  recPerson;
	  String  remark;
	  
	  @Id
	  @Column(name = "rec_id")
	  @GeneratedValue(generator = "system-uuid")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")
	  public String getRecId(){
	  	return this.recId;
	  }
	  	
	  public void setRecId(String recId){
		  this.recId =  recId;
	  }
	  
	  @Column(name = "contract_id")
	  public String getContractId(){
		  return this.contractId;
	  }
	  
	  public void setContractId(String contractId){
		  this.contractId =  contractId;
	  }
	  @Column(name = "contract_code")
	  public String getContractCode(){
		  return this.contractCode;
	  }
	  
	  public void setContractCode(String contractCode){
		  this.contractCode =  contractCode;
	  }
	  @Column(name = "contract_deal_content")
	  public String getContractDealContent(){
		  return this.contractDealContent;
	  }
	  
	  public void setContractDealContent(String contractDealContent){
		  this.contractDealContent =  contractDealContent;
	  }
	  @Column(name = "rec_time")
	  public Date getRecTime(){
		  return this.recTime;
	  }
	  
	  public void setRecTime(Date recTime){
		  this.recTime =  recTime;
	  }
	  @Column(name = "rec_person")
	  public String getRecPerson(){
		  return this.recPerson;
	  }
	  
	  public void setRecPerson(String recPerson){
		  this.recPerson =  recPerson;
	  }
	  @Column(name = "remark")
	  public String getRemark(){
		  return this.remark;
	  }
	  
	  public void setRemark(String remark){
		  this.remark =  remark;
	  }
	  @Column(name = "contract_name")
	  public String getContractName() {
		  return contractName;
	  }
	  
	  public void setContractName(String contractName) {
		  this.contractName = contractName;
	  }
	  
}
