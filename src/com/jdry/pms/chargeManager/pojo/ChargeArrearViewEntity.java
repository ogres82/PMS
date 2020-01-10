package com.jdry.pms.chargeManager.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "v_charge_arrearage")
public class ChargeArrearViewEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5007626545509761181L;
	private String owner_id; // 业主编号
	private String owner_name; // 业主姓名
	private String room_id; // 房间id
	private String room_no; // 房间编号
	private Date begin_time; // 计费开始时间
	private Date end_time; // 计费结束时间
	private String phone; // 电话
	private BigDecimal delay_pay; // 滞纳金
	private BigDecimal reduce_pay; // 减免金额
	private BigDecimal arrearage_amount; // 欠费/应收金额
	private BigDecimal total_pay; // 汇总金额
	private String community_name; // 所属小区
	private String storied_build_name; // 所属单元
	private String room_type;

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

	public Date getBegin_time() {
		return begin_time;
	}

	public void setBegin_time(Date begin_time) {
		this.begin_time = begin_time;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public BigDecimal getDelay_pay() {
		return delay_pay;
	}

	public void setDelay_pay(BigDecimal delay_pay) {
		this.delay_pay = delay_pay;
	}

	public BigDecimal getReduce_pay() {
		return reduce_pay;
	}

	public void setReduce_pay(BigDecimal reduce_pay) {
		this.reduce_pay = reduce_pay;
	}

	public BigDecimal getArrearage_amount() {
		return arrearage_amount;
	}

	public void setArrearage_amount(BigDecimal arrearage_amount) {
		this.arrearage_amount = arrearage_amount;
	}

	public BigDecimal getTotal_pay() {
		return total_pay;
	}

	public void setTotal_pay(BigDecimal total_pay) {
		this.total_pay = total_pay;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	} // 汇总金额

	public String getCommunity_name() {
		return community_name;
	}

	public void setCommunity_name(String community_name) {
		this.community_name = community_name;
	}

	public String getStoried_build_name() {
		return storied_build_name;
	}

	public void setStoried_build_name(String storied_build_name) {
		this.storied_build_name = storied_build_name;
	}

	public String getRoom_type() {
		return room_type;
	}

	public void setRoom_type(String room_type) {
		this.room_type = room_type;
	}

}
