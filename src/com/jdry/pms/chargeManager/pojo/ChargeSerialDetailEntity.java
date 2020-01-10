package com.jdry.pms.chargeManager.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_charge_serial_detail")
public class ChargeSerialDetailEntity implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7041361865317558082L;
	private String charge_id;			//账单id
	private String charge_serial_id;			//账单编号
	private Date	update_date;		//更新时间
	
	@Id
	public String getCharge_id() {
		return charge_id;
	}
	public void setCharge_id(String charge_id) {
		this.charge_id = charge_id;
	}
	@Id
	public String getCharge_serial_id() {
		return charge_serial_id;
	}
	public void setCharge_serial_id(String charge_serial_id) {
		this.charge_serial_id = charge_serial_id;
	}
	public Date getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}
	
	
	
}
