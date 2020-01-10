package com.jdry.pms.basicInfo.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "v_room_charge_type")
public class VRoomChargeTypeRela implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7260897057890087275L;
	
	
	private String charge_type_id;		//主键,收费项目ID
	private String charge_type_no;		//收费项目编号
	private String room_id;				//房间id
	private String room_no;				//房间编号
	private String owner_id;			//业主ID
	private	String update_emp_id;		//录入人员ID
	private String charge_type_name;	//录入时间
	
	private String charge_mode;			//计费方式 01:建面 02定额 03公式
	private float charge_price;			//单价
	private Date update_date;			//录入时间
	private String charge_way;			//收费方式 01周期性 02临时性
	private String charge_type;			//收费类型 01:正常 02押金 03预收款
	private	int	charge_cycle_count;		//计费周期数
	private String	charge_cycle_unit;	//周期单位 01:日	02周	03月	04年
	private float	amount;
	private String cname;	
	
	private Date chargeDate;
	private String chargeState;
	
	@Id
	@Column(name = "charge_type_id")
	public String getCharge_type_id() {
		return charge_type_id;
	}
	public void setCharge_type_id(String charge_type_id) {
		this.charge_type_id = charge_type_id;
	}
	@Column(name = "charge_type_no")
	public String getCharge_type_no() {
		return charge_type_no;
	}
	public void setCharge_type_no(String charge_type_no) {
		this.charge_type_no = charge_type_no;
	}
	
	@Id
	@Column(name = "room_id")
	public String getRoom_id() {
		return room_id;
	}
	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}
	@Column(name = "room_no")
	public String getRoom_no() {
		return room_no;
	}
	public void setRoom_no(String room_no) {
		this.room_no = room_no;
	}
	@Column(name = "owner_id")
	public String getOwner_id() {
		return owner_id;
	}
	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}
	@Column(name = "update_emp_id")
	public String getUpdate_emp_id() {
		return update_emp_id;
	}
	public void setUpdate_emp_id(String update_emp_id) {
		this.update_emp_id = update_emp_id;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Column(name = "charge_type_name")
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
	public float getCharge_price() {
		return charge_price;
	}
	public void setCharge_price(float charge_price) {
		this.charge_price = charge_price;
	}
	public Date getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
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
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	@Column(name = "CNAME_")
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	
	@Column(name = "charge_date")
	public Date getChargeDate() {
		return chargeDate;
	}
	public void setChargeDate(Date chargeDate) {
		this.chargeDate = chargeDate;
	}
	
	@Column(name = "charge_state")
	public String getChargeState() {
		return chargeState;
	}
	public void setChargeState(String chargeState) {
		this.chargeState = chargeState;
	}
	
}
