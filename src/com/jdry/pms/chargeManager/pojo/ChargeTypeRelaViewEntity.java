package com.jdry.pms.chargeManager.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "v_charge_type_room_rela")
public class ChargeTypeRelaViewEntity implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 739845543676157201L;
	
	private String belong_unit;			//所属小区
	private String storied_build_name;	//所属楼宇
	private String room_no;				//房间编号
	private String charge_type_no;		//收费项目编号
	private String charge_type_name;	//收费项目名称
	private String charge_mode;			//计费方式 01:建面 02定额 03公式
	private BigDecimal charge_price;			//单价
	private String charge_way;			//收费方式 01周期性 02临时性
	private String charge_type;			//收费类型 01:正常 02押金 03预收款
	private	int	charge_cycle_count;		//计费周期数
	private String	charge_cycle_unit;	//周期单位 01:日	02周	03月	04年
	private String	room_id;	//
	private String	charge_type_id;	//
	private String	owner_id;	//
	private String	owner_name;	//
	private String	type_flag;
	
	private String drop_charge_mode;			//计费方式 01:建面 02定额 03公式
	private String drop_charge_way;			//收费方式 01周期性 02临时性
	private String drop_charge_type;			//收费类型 01:正常 02押金 03预收款
	private String	drop_charge_cycle_unit;	  //周期单位 01:日	02周	03月	04年
	
	@Id	
	public String getRoom_id() {
		return room_id;
	}
	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}
	@Id
	public String getCharge_type_id() {
		return charge_type_id;
	}
	public void setCharge_type_id(String charge_type_id) {
		this.charge_type_id = charge_type_id;
	}
	public String getOwner_id() {
		return owner_id;
	}
	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}
	public String getOwner_name() {
		return owner_name;
	}
	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}
	public String getBelong_unit() {
		return belong_unit;
	}
	public void setBelong_unit(String belong_unit) {
		this.belong_unit = belong_unit;
	}
	
	public String getRoom_no() {
		return room_no;
	}
	public void setRoom_no(String room_no) {
		this.room_no = room_no;
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
	public String getCharge_mode() {
		return charge_mode;
	}
	public void setCharge_mode(String charge_mode) {
		this.charge_mode = charge_mode;
	}
	public BigDecimal getCharge_price() {
		return charge_price;
	}
	public void setCharge_price(BigDecimal charge_price) {
		this.charge_price = charge_price;
	}
	public String getCharge_way() {
		return charge_way;
	}
	public void setCharge_way(String charge_way) {
		this.charge_way = charge_way;
	}
	public String getCharge_type() {
		return charge_type;
	}
	public void setCharge_type(String charge_type) {
		this.charge_type = charge_type;
	}
	public int getCharge_cycle_count() {
		return charge_cycle_count;
	}
	public void setCharge_cycle_count(int charge_cycle_count) {
		this.charge_cycle_count = charge_cycle_count;
	}
	public String getCharge_cycle_unit() {
		return charge_cycle_unit;
	}
	public void setCharge_cycle_unit(String charge_cycle_unit) {
		this.charge_cycle_unit = charge_cycle_unit;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getDrop_charge_mode() {
		return drop_charge_mode;
	}
	public void setDrop_charge_mode(String drop_charge_mode) {
		this.drop_charge_mode = drop_charge_mode;
	}
	public String getDrop_charge_way() {
		return drop_charge_way;
	}
	public void setDrop_charge_way(String drop_charge_way) {
		this.drop_charge_way = drop_charge_way;
	}
	public String getDrop_charge_type() {
		return drop_charge_type;
	}
	public void setDrop_charge_type(String drop_charge_type) {
		this.drop_charge_type = drop_charge_type;
	}
	public String getDrop_charge_cycle_unit() {
		return drop_charge_cycle_unit;
	}
	public void setDrop_charge_cycle_unit(String drop_charge_cycle_unit) {
		this.drop_charge_cycle_unit = drop_charge_cycle_unit;
	}
	public String getType_flag() {
		return type_flag;
	}
	public void setType_flag(String type_flag) {
		this.type_flag = type_flag;
	}
	public String getStoried_build_name() {
		return storied_build_name;
	}
	public void setStoried_build_name(String storied_build_name) {
		this.storied_build_name = storied_build_name;
	}
}
