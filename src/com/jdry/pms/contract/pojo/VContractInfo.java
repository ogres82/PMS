package com.jdry.pms.contract.pojo;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "v_contract_info")
public class VContractInfo {

	
	
	  String  contractId;
	  String  contractCode;
	  String  contractName;
	  String  contractType;
	  String  contractTypeName;
	  String  contractDetail;
	  String  contractPbName;
	  String  contractPbLinker;
	  String  contractPbPhone;
	  Date  contractSignDate;
	  Date  contractEndDate;
	  Float  contractPeriod;
	  Float  contractPrice;
	  String  contractStatus;
	  String  contractStatusName;
	  String  remark;
	  
	  
	  @Id
	  @Column(name = "contract_id")
	  @GeneratedValue(generator = "system-uuid")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")
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
	  @Column(name = "contract_name")
	  public String getContractName(){
		  return this.contractName;
	  }
	  
	  public void setContractName(String contractName){
		  this.contractName =  contractName;
	  }
	  @Column(name = "contract_type")
	  public String getContractType(){
		  return this.contractType;
	  }
	  
	  public void setContractType(String contractType){
		  this.contractType =  contractType;
	  }
	  @Column(name = "contract_detail")
	  public String getContractDetail(){
		  return this.contractDetail;
	  }
	  
	  public void setContractDetail(String contractDetail){
		  this.contractDetail =  contractDetail;
	  }
	  @Column(name = "contract_pB_name")
	  public String getContractPbName(){
		  return this.contractPbName;
	  }
	  
	  public void setContractPbName(String contractPbName){
		  this.contractPbName =  contractPbName;
	  }
	  @Column(name = "contract_pB_linker")
	  public String getContractPbLinker(){
		  return this.contractPbLinker;
	  }
	  
	  public void setContractPbLinker(String contractPbLinker){
		  this.contractPbLinker =  contractPbLinker;
	  }
	  @Column(name = "contract_pB_phone")
	  public String getContractPbPhone(){
		  return this.contractPbPhone;
	  }
	  
	  public void setContractPbPhone(String contractPbPhone){
		  this.contractPbPhone =  contractPbPhone;
	  }
	  @Column(name = "contract_sign_date")
	  public Date getContractSignDate(){
		  return this.contractSignDate;
	  }
	  
	  public void setContractSignDate(Date contractSignDate){
		  this.contractSignDate =  contractSignDate;
	  }
	  @Column(name = "contract_end_date")
	  public Date getContractEndDate(){
		  return this.contractEndDate;
	  }
	  
	  public void setContractEndDate(Date contractEndDate){
		  this.contractEndDate =  contractEndDate;
	  }
	  @Column(name = "contract_period")
	  public Float getContractPeriod(){
		  return this.contractPeriod;
	  }
	  
	  public void setContractPeriod(Float contractPeriod){
		  this.contractPeriod =  contractPeriod;
	  }
	  @Column(name = "contract_price")
	  public Float getContractPrice(){
		  return this.contractPrice;
	  }
	  
	  public void setContractPrice(Float contractPrice){
		  this.contractPrice =  contractPrice;
	  }
	  @Column(name = "contract_status")
	  public String getContractStatus(){
		  return this.contractStatus;
	  }
	  
	  public void setContractStatus(String contractStatus){
		  this.contractStatus =  contractStatus;
	  }
	  @Column(name = "remark")
	  public String getRemark(){
		  return this.remark;
	  }
	  
	  public void setRemark(String remark){
		  this.remark =  remark;
	  }
	  @Column(name = "contract_type_name")
	  public String getContractTypeName() {
		  return contractTypeName;
	  }
	  
	  public void setContractTypeName(String contractTypeName) {
		  this.contractTypeName = contractTypeName;
	  }
	  @Column(name = "contract_status_name")
	  public String getContractStatusName() {
		  return contractStatusName;
	  }
	  
	  public void setContractStatusName(String contractStatusName) {
		  this.contractStatusName = contractStatusName;
	  }
}
