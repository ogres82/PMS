package com.jdry.pms.chargeManager.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "v_charge_info")
public class ChargeInfoViewEntity implements Serializable {

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
	private BigDecimal price;				//单价
	private BigDecimal count;					//数量
	private BigDecimal rate;					//倍率
	private BigDecimal receive_amount;		//应收金额
	private BigDecimal paid_amount;			//实收金额
	private BigDecimal arrearage_amount;		//欠费金额
	private Date paid_date;				//收款日期
	private String paid_mode;				//收款方式
	private String oper_emp_id;			//操作人
	private String state;				//账单状态
	private Date	update_date;		//更新时间
	private	int		delete_id;			//删除标识，只是逻辑删除 0：未删除，1：已删除
	private String remark;				//备注
	private String data_from;			//数据来源：01系统自动，02前台手动
	
	private String drop_paid_mode;				//收款方式
	private String drop_data_from;				//数据来源：01系统自动，02前台手动
	private String work_id;				//工单号
	

	private String community_name;				//小区名称
	private String storied_build_name;				//楼宇名称
	private String room_type;				//楼宇名称
	
	private String build_id;							//楼盘id
	private String build_name;						//楼盘名称
	
	private String community_id;					//小区id
	private String storied_build_id;				//楼宇id
	
		
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
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getCount() {
		return count;
	}
	public void setCount(BigDecimal count) {
		this.count = count;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public BigDecimal getReceive_amount() {
		return receive_amount;
	}
	public void setReceive_amount(BigDecimal receive_amount) {
		this.receive_amount = receive_amount;
	}
	public BigDecimal getPaid_amount() {
		return paid_amount;
	}
	public String getRoom_type() {
		return room_type;
	}
	public void setRoom_type(String room_type) {
		this.room_type = room_type;
	}
	public void setPaid_amount(BigDecimal paid_amount) {
		this.paid_amount = paid_amount;
	}
	public BigDecimal getArrearage_amount() {
		return arrearage_amount;
	}
	public void setArrearage_amount(BigDecimal arrearage_amount) {
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
	public String getDrop_paid_mode() {
		return drop_paid_mode;
	}
	public void setDrop_paid_mode(String drop_paid_mode) {
		this.drop_paid_mode = drop_paid_mode;
	}
	public String getData_from() {
		return data_from;
	}
	public void setData_from(String data_from) {
		this.data_from = data_from;
	}
	public String getDrop_data_from() {
		return drop_data_from;
	}
	public void setDrop_data_from(String drop_data_from) {
		this.drop_data_from = drop_data_from;
	}
	public String getWork_id() {
		return work_id;
	}
	public void setWork_id(String work_id) {
		this.work_id = work_id;
	}
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
	public String getBuild_id() {
		return build_id;
	}
	public void setBuild_id(String build_id) {
		this.build_id = build_id;
	}
	public String getBuild_name() {
		return build_name;
	}
	public void setBuild_name(String build_name) {
		this.build_name = build_name;
	}
	public String getCommunity_id() {
		return community_id;
	}
	public void setCommunity_id(String community_id) {
		this.community_id = community_id;
	}
	public String getStoried_build_id() {
		return storied_build_id;
	}
	public void setStoried_build_id(String storied_build_id) {
		this.storied_build_id = storied_build_id;
	}
	
	
	
	
	
	
	
}
