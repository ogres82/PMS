package com.jdry.pms.basicInfo.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "v_charge_vs_type")
public class VChargeTypeInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6266835945884788136L;
	
	private String charge_id;			//账单id
	private String charge_no;			//账单编号
	private String owner_id;			//业主编号
	private String owner_name;			//业主姓名
	private String charge_type_no;		//收费项目编号
	private String charge_type_name;	//收费项目名称
	private String room_id;				//房间id
	private String room_no;				//房间编号
	private Date begin_time;			//计费开始时间
	private Date end_time;				//计费结束时间
	private float price;				//单价
	private float count;					//数量
	private float rate;					//倍率
	private float receive_amount;		//应收金额
	private float paid_amount;			//实收金额
	private float arrearage_amount;		//欠费金额
	private Date paid_date;				//收款日期
	private String paid_mode;				//收款方式
	private String oper_emp_id;			//操作人
	private String state;				//账单状态
	private Date	update_date;		//更新时间
	private	int		delete_id;			//删除标识，只是逻辑删除 0：未删除，1：已删除
	private String remark;				//备注
	private String data_from;			//数据来源：01系统自动，02前台手动
	
	private String charge_mode;				//收款方式
	private String charge_type;				//数据来源：01系统自动，02前台手动
	private String charge_way;
		
	@Id
	public String getCharge_id() {
		return charge_id;
	}
	public void setCharge_id(String charge_id) {
		this.charge_id = charge_id;
	}
	public String getCharge_no() {
		return charge_no;
	}
	public void setCharge_no(String charge_no) {
		this.charge_no = charge_no;
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
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getCount() {
		return count;
	}
	public void setCount(float count) {
		this.count = count;
	}
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
		this.rate = rate;
	}
	public float getReceive_amount() {
		return receive_amount;
	}
	public void setReceive_amount(float receive_amount) {
		this.receive_amount = receive_amount;
	}
	public float getPaid_amount() {
		return paid_amount;
	}
	public void setPaid_amount(float paid_amount) {
		this.paid_amount = paid_amount;
	}
	public float getArrearage_amount() {
		return arrearage_amount;
	}
	public void setArrearage_amount(float arrearage_amount) {
		this.arrearage_amount = arrearage_amount;
	}
	public Date getPaid_date() {
		return paid_date;
	}
	public void setPaid_date(Date paid_date) {
		this.paid_date = paid_date;
	}
	public String getPaid_mode() {
		return paid_mode;
	}
	public void setPaid_mode(String paid_mode) {
		this.paid_mode = paid_mode;
	}
	public String getOper_emp_id() {
		return oper_emp_id;
	}
	public void setOper_emp_id(String oper_emp_id) {
		this.oper_emp_id = oper_emp_id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}
	public int getDelete_id() {
		return delete_id;
	}
	public void setDelete_id(int delete_id) {
		this.delete_id = delete_id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getData_from() {
		return data_from;
	}
	public void setData_from(String data_from) {
		this.data_from = data_from;
	}
	public String getCharge_mode() {
		return charge_mode;
	}
	public void setCharge_mode(String charge_mode) {
		this.charge_mode = charge_mode;
	}
	public String getCharge_type() {
		return charge_type;
	}
	public void setCharge_type(String charge_type) {
		this.charge_type = charge_type;
	}
	public String getCharge_way() {
		return charge_way;
	}
	public void setCharge_way(String charge_way) {
		this.charge_way = charge_way;
	}
	
	
}
