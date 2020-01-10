package com.jdry.pms.assignWork.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "v_t_r_assign_main")
public class VWorkMainEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7651939793921701587L;
	private String rpt_kid;
	private String rpt_id;
	private String event_source;
	private String event_source_name;
	private String event_type;
	private String event_type_name;
	private String addres;
	private String createby;
	private String createby_name;
	private String createTime;
	private String finishTime;
	private String rpt_name;
	private String event_state;
	private String event_state_name;
	
	private String order_state;
	
	public String getRpt_name() {
		return rpt_name;
	}
	public void setRpt_name(String rpt_name) {
		this.rpt_name = rpt_name;
	}
	@Id
	public String getRpt_kid() {
		return rpt_kid;
	}
	public void setRpt_kid(String rpt_kid) {
		this.rpt_kid = rpt_kid;
	}
	public String getRpt_id() {
		return rpt_id;
	}
	public void setRpt_id(String rpt_id) {
		this.rpt_id = rpt_id;
	}
	public String getEvent_source() {
		return event_source;
	}
	public void setEvent_source(String event_source) {
		this.event_source = event_source;
	}
	public String getEvent_source_name() {
		return event_source_name;
	}
	public void setEvent_source_name(String event_source_name) {
		this.event_source_name = event_source_name;
	}
	public String getEvent_type() {
		return event_type;
	}
	public void setEvent_type(String event_type) {
		this.event_type = event_type;
	}
	public String getEvent_type_name() {
		return event_type_name;
	}
	public void setEvent_type_name(String event_type_name) {
		this.event_type_name = event_type_name;
	}
	public String getAddres() {
		return addres;
	}
	public void setAddres(String addres) {
		this.addres = addres;
	}
	public String getCreateby() {
		return createby;
	}
	public void setCreateby(String createby) {
		this.createby = createby;
	}
	public String getCreateby_name() {
		return createby_name;
	}
	public void setCreateby_name(String createby_name) {
		this.createby_name = createby_name;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	public String getEvent_state() {
		return event_state;
	}
	public void setEvent_state(String event_state) {
		this.event_state = event_state;
	}
	
	public String getOrder_state() {
		return order_state;
	}
	public void setOrder_state(String order_state) {
		this.order_state = order_state;
	}
	public String getEvent_state_name() {
		return event_state_name;
	}
	public void setEvent_state_name(String event_state_name) {
		this.event_state_name = event_state_name;
	}
	
	
}
