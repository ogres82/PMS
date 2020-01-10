package com.jdry.pms.assignWork.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
//v_r_t_assign_visit
@Entity
@Table(name = "t_r_assign_newvisit")
public class VWorkVisitEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3576164369915839458L;
	
	
	private String rpt_kid;
	private String rpt_id;
	private String rpt_name;
	private String in_call;
	private String addres;
	private String event_type;
	private String event_source;
	private String event_type_name;
	private String event_source_name;
	private String dispatch_status;
	private String dispatch_handle_id;
	private String dispatch_id;
	private String dispatch_status_name;
	private String createTime;
	private String state;
	private String createby;
	private String createby_name;
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	private String dispatch_result;
	public String getDispatch_result() {
		return dispatch_result;
	}
	public void setDispatch_result(String dispatch_result) {
		this.dispatch_result = dispatch_result;
	}
	
	
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
	public String getRpt_name() {
		return rpt_name;
	}
	public void setRpt_name(String rpt_name) {
		this.rpt_name = rpt_name;
	}
	public String getIn_call() {
		return in_call;
	}
	public void setIn_call(String in_call) {
		this.in_call = in_call;
	}
	public String getAddres() {
		return addres;
	}
	public void setAddres(String addres) {
		this.addres = addres;
	}
	public String getEvent_type() {
		return event_type;
	}
	public void setEvent_type(String event_type) {
		this.event_type = event_type;
	}
	public String getEvent_source() {
		return event_source;
	}
	public void setEvent_source(String event_source) {
		this.event_source = event_source;
	}
	public String getEvent_type_name() {
		return event_type_name;
	}
	public void setEvent_type_name(String event_type_name) {
		this.event_type_name = event_type_name;
	}
	public String getEvent_source_name() {
		return event_source_name;
	}
	public void setEvent_source_name(String event_source_name) {
		this.event_source_name = event_source_name;
	}
	public String getDispatch_status() {
		return dispatch_status;
	}
	public void setDispatch_status(String dispatch_status) {
		this.dispatch_status = dispatch_status;
	}
	public String getDispatch_handle_id() {
		return dispatch_handle_id;
	}
	public void setDispatch_handle_id(String dispatch_handle_id) {
		this.dispatch_handle_id = dispatch_handle_id;
	}
	public String getDispatch_id() {
		return dispatch_id;
	}
	public void setDispatch_id(String dispatch_id) {
		this.dispatch_id = dispatch_id;
	}
	public String getDispatch_status_name() {
		return dispatch_status_name;
	}
	public void setDispatch_status_name(String dispatch_status_name) {
		this.dispatch_status_name = dispatch_status_name;
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
	
	
}
