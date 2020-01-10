package com.jdry.pms.houseWork.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 描述：家政派工实体
 * 
 * @author hezuping
 * 
 */
@Entity
@Table(name = "t_housework_dispatch")
public class HouseWorkEventSendEntity implements Serializable {
	private String id;// 派工单ID
	private String send_no;// 派工单编号
	private String event_id;// 事件单ID
	private String oper_id;// 处理人
	private String oper_name;
	private Date send_time;// 派工时间
	private Date arrv_time;
	private Date delete_time;//消单时间
	private String send_state;
	private String handle_content;// 处理内容
	
	private Double houseKeepingPay;//家政费用

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "send_no")
	public String getSend_no() {
		return send_no;
	}

	public void setSend_no(String send_no) {
		this.send_no = send_no;
	}

	@Column(name = "event_id")
	public String getEvent_id() {
		return event_id;
	}

	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}

	@Column(name = "oper_id")
	public String getOper_id() {
		return oper_id;
	}

	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}

	@Column(name = "send_time")
	public Date getSend_time() {
		return send_time;
	}

	public void setSend_time(Date send_time)
	{
		this.send_time = send_time;
	}

	@Column(name = "arrv_time")
	public Date getArrv_time() {
		return arrv_time;
	}

	public void setArrv_time(Date arrv_time) {
		this.arrv_time = arrv_time;
	}

	@Column(name = "delete_time")
	public Date getDelete_time() {
		return delete_time;
	}

	public void setDelete_time(Date delete_time) {
		this.delete_time = delete_time;
	}

	@Column(name = "send_state")
	public String getSend_state() {
		return send_state;
	}

	public void setSend_state(String send_state) {
		this.send_state = send_state;
	}

	@Column(name = "handle_content")
	public String getHandle_content() {
		return handle_content;
	}

	public void setHandle_content(String handle_content) {
		this.handle_content = handle_content;
	}

	@Column(name = "oper_name")
	public String getOper_name() {
		return oper_name;
	}

	public void setOper_name(String oper_name) {
		this.oper_name = oper_name;
	}

	@Column(name = "houseKeepingPay")
	public Double getHouseKeepingPay() {
		return houseKeepingPay;
	}

	public void setHouseKeepingPay(Double houseKeepingPay) {
		this.houseKeepingPay = houseKeepingPay;
	}

}
