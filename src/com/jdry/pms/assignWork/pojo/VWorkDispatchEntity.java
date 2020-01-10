package com.jdry.pms.assignWork.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "v_t_r_assign_dispatch")
public class VWorkDispatchEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6539304135827975002L;
	private String dispatch_kid;
	private String dispatch_id;
	private String addres;
	private String dispatch_expense;
	private String dispatch_status;
	private String dispatch_status_name;
	private String createby;
	private String createby_name;
	private String createTime;
	private String dispatch_handle_id;
	private String dispatch_handle_name;
	private String dispatch_finish_time;
	private String mtn_id;
	private String rpt_name;
	private String in_call;
	
	@Id
	public String getDispatch_kid() {
		return dispatch_kid;
	}
	public void setDispatch_kid(String dispatch_kid) {
		this.dispatch_kid = dispatch_kid;
	}
	public String getDispatch_id() {
		return dispatch_id;
	}
	public void setDispatch_id(String dispatch_id) {
		this.dispatch_id = dispatch_id;
	}
	public String getAddres() {
		return addres;
	}
	public void setAddres(String addres) {
		this.addres = addres;
	}
	public String getDispatch_expense() {
		return dispatch_expense;
	}
	public void setDispatch_expense(String dispatch_expense) {
		this.dispatch_expense = dispatch_expense;
	}
	public String getDispatch_status() {
		return dispatch_status;
	}
	public void setDispatch_status(String dispatch_status) {
		this.dispatch_status = dispatch_status;
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getDispatch_handle_id() {
		return dispatch_handle_id;
	}
	public void setDispatch_handle_id(String dispatch_handle_id) {
		this.dispatch_handle_id = dispatch_handle_id;
	}
	public String getDispatch_handle_name() {
		return dispatch_handle_name;
	}
	public void setDispatch_handle_name(String dispatch_handle_name) {
		this.dispatch_handle_name = dispatch_handle_name;
	}
	public String getDispatch_finish_time() {
		return dispatch_finish_time;
	}
	public void setDispatch_finish_time(String dispatch_finish_time) {
		this.dispatch_finish_time = dispatch_finish_time;
	}
	public String getMtn_id() {
		return mtn_id;
	}
	public void setMtn_id(String mtn_id) {
		this.mtn_id = mtn_id;
	}
	public String getRpt_name() {
		return rpt_name;
	}
	public String getIn_call() {
		return in_call;
	}
	public void setRpt_name(String rpt_name) {
		this.rpt_name = rpt_name;
	}
	public void setIn_call(String in_call) {
		this.in_call = in_call;
	}
	
}
