package com.jdry.pms.chargeManager.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ChargeTypeRelaBatchEntity implements Serializable {

	
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 462790607852664033L;
	private String belong_unit;			//所属单位
	private String belong_unit_id;			//所属单位id
	private String charge_type_id;				//收费项目id
	private String charge_type_no;		//收费项目编号
	private String charge_type_name;	//收费项目名称
	private String storied_build_id;	//楼宇
	private String storied_build_name;	//楼宇
	
	public String getBelong_unit() {
		return belong_unit;
	}
	public void setBelong_unit(String belong_unit) {
		this.belong_unit = belong_unit;
	}
	public String getBelong_unit_id() {
		return belong_unit_id;
	}
	public void setBelong_unit_id(String belong_unit_id) {
		this.belong_unit_id = belong_unit_id;
	}
	@Id
	public String getCharge_type_id() {
		return charge_type_id;
	}
	public void setCharge_type_id(String charge_type_id) {
		this.charge_type_id = charge_type_id;
	}
	public String getCharge_type_no() {
		return charge_type_no;
	}
	public void setCharge_type_no(String charge_type_no) {
		this.charge_type_no = charge_type_no;
	}
	public String getCharge_type_name() {
		return charge_type_name;
	}
	public void setCharge_type_name(String charge_type_name) {
		this.charge_type_name = charge_type_name;
	}
	public String getStoried_build_id() {
		return storied_build_id;
	}
	public void setStoried_build_id(String storied_build_id) {
		this.storied_build_id = storied_build_id;
	}
	public String getStoried_build_name() {
		return storied_build_name;
	}
	public void setStoried_build_name(String storied_build_name) {
		this.storied_build_name = storied_build_name;
	}
	
	
	
}
