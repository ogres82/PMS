package com.jdry.pms.chargeManager.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_charge_type_room_rela")
public class ChargeTypeRoomRelaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5594900948052611283L;
	/**
	 * 
	 */
	
	
	private String	type_flag;
	private String charge_type_id;		//主键,收费项目ID
	private String charge_type_no;		//收费项目编号
	private String room_id;				//房间id
	private String room_no;				//房间编号
	private String owner_id;			//业主ID
	private	String update_emp_id = "admin";		//录入人员ID
	private Date update_date = new Date();			//录入时间
	private Date charge_date;			//起征时间
	private String charge_state;		//是否生效费用
	private BigDecimal amount = new BigDecimal(0);          //余额
    private String is_del = "0";				//逻辑删除字段 0：未删除；1：删除

    
	

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
	
	@Id
	public String getRoom_id() {
		return room_id;
	}
	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}
	public String getRoom_no() {
		return room_no;
	}
	public void setRoom_no(String room_no) {
		this.room_no = room_no;
	}
	public String getOwner_id() {
		return owner_id;
	}
	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}
	public String getUpdate_emp_id() {
		return update_emp_id;
	}
	public void setUpdate_emp_id(String update_emp_id) {
		this.update_emp_id = update_emp_id;
	}
	public Date getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}
	public String getType_flag() {
		return type_flag;
	}
	public void setType_flag(String type_flag) {
		this.type_flag = type_flag;
	}
	
	public Date getCharge_date() {
		return charge_date;
	}
	public void setCharge_date(Date charge_date) {
		this.charge_date = charge_date;
	}
	public String getCharge_state() {
		return charge_state;
	}
	public void setCharge_state(String charge_state) {
		this.charge_state = charge_state;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getIs_del() {
		return is_del;
	}
	public void setIs_del(String is_del) {
		this.is_del = is_del;
	}
}
