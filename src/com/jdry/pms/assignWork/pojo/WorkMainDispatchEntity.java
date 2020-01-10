package com.jdry.pms.assignWork.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_R_MAINTAIN_DISPATCH")
public class WorkMainDispatchEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8961641399280709731L;
	private String dispatch_kid;// key
	private String dispatch_id;// 派工单号 varchar2(15)
	private String mtn_id;// 关联报修单号 varchar2(15)
	private Date dispatch_time;// 派工时间 date
	private String mtn_detail;// 详细描述 varchar2(255)
	private String mtn_emergency;// 紧急程度 varchar2(5)
	private String mtn_type;// 维修类别 varchar2(5)
	private String mtn_priority;// 业主维修意见 varchar2(5)--同意，不同意
	private String dispatch_result;// 处理结果描述 varchar2(255)
	private String dispatch_status;// 派工单状态 varchar2(5)
	private Date dispatch_arrive_time;// 到达现场时间 date
	private String dispatch_handle_name;// 处理人姓名 varchar2(15)
	private String dispatch_handle_id;// 处理人id varchar2(15)
	private Date dispatch_finish_time;// 消单时间 date
	private String dispatch_tools;// 维修用料 varchar2(100)
	private String dispatch_expense;// 维修费用 varchar2(100总额
	private Date dispatch_sl_time;// 受理时间 date
	private String dispatch_degree;// 满意度
	private String dispatch_solve;// 是否解决
	private String dispatch_visit_lev;// 回访业主评级
	private String dispatch_visit_record;// 回访记录
	private String dispatch_visit_recording;// 回访记录录音
	private String createby;// 新增人 varchar2(5)
	private Date createTime;// 新增时间 varchar2(5)
	private Double material_cost;//材料费用
	private Double labor_cost;//材料费用
	private String dispatch_evaluate;
	private Date dispatch_visit_time;//回访时间
	private String finishImgUrl;
	private String rejectReason;
	

	public Date getDispatch_sl_time() {
		return dispatch_sl_time;
	}

	public void setDispatch_sl_time(Date dispatch_sl_time) {
		this.dispatch_sl_time = dispatch_sl_time;
	}

	public String getDispatch_degree() {
		return dispatch_degree;
	}

	public void setDispatch_degree(String dispatch_degree) {
		this.dispatch_degree = dispatch_degree;
	}

	public String getDispatch_solve() {
		return dispatch_solve;
	}

	public void setDispatch_solve(String dispatch_solve) {
		this.dispatch_solve = dispatch_solve;
	}

	public String getDispatch_visit_lev() {
		return dispatch_visit_lev;
	}

	public void setDispatch_visit_lev(String dispatch_visit_lev) {
		this.dispatch_visit_lev = dispatch_visit_lev;
	}

	public String getDispatch_visit_record() {
		return dispatch_visit_record;
	}

	public void setDispatch_visit_record(String dispatch_visit_record) {
		this.dispatch_visit_record = dispatch_visit_record;
	}

	public String getDispatch_visit_recording() {
		return dispatch_visit_recording;
	}

	public void setDispatch_visit_recording(String dispatch_visit_recording) {
		this.dispatch_visit_recording = dispatch_visit_recording;
	}

	public String getCreateby() {
		return createby;
	}

	public void setCreateby(String createby) {
		this.createby = createby;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

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

	public String getMtn_id() {
		return mtn_id;
	}

	public void setMtn_id(String mtn_id) {
		this.mtn_id = mtn_id;
	}

	public Date getDispatch_time() {
		return dispatch_time;
	}

	public void setDispatch_time(Date dispatch_time) {
		this.dispatch_time = dispatch_time;
	}

	public String getMtn_detail() {
		return mtn_detail;
	}

	public void setMtn_detail(String mtn_detail) {
		this.mtn_detail = mtn_detail;
	}

	public String getMtn_emergency() {
		return mtn_emergency;
	}

	public void setMtn_emergency(String mtn_emergency) {
		this.mtn_emergency = mtn_emergency;
	}

	public String getMtn_type() {
		return mtn_type;
	}

	public void setMtn_type(String mtn_type) {
		this.mtn_type = mtn_type;
	}

	public String getMtn_priority() {
		return mtn_priority;
	}

	public void setMtn_priority(String mtn_priority) {
		this.mtn_priority = mtn_priority;
	}

	public String getDispatch_result() {
		return dispatch_result;
	}

	public void setDispatch_result(String dispatch_result) {
		this.dispatch_result = dispatch_result;
	}

	public String getDispatch_status() {
		return dispatch_status;
	}

	public void setDispatch_status(String dispatch_status) {
		this.dispatch_status = dispatch_status;
	}

	public Date getDispatch_arrive_time() {
		return dispatch_arrive_time;
	}

	public void setDispatch_arrive_time(Date dispatch_arrive_time) {
		this.dispatch_arrive_time = dispatch_arrive_time;
	}

	public String getDispatch_handle_name() {
		return dispatch_handle_name;
	}

	public void setDispatch_handle_name(String dispatch_handle_name) {
		this.dispatch_handle_name = dispatch_handle_name;
	}

	public String getDispatch_handle_id() {
		return dispatch_handle_id;
	}

	public void setDispatch_handle_id(String dispatch_handle_id) {
		this.dispatch_handle_id = dispatch_handle_id;
	}

	public Date getDispatch_finish_time() {
		return dispatch_finish_time;
	}

	public void setDispatch_finish_time(Date dispatch_finish_time) {
		this.dispatch_finish_time = dispatch_finish_time;
	}

	public String getDispatch_tools() {
		return dispatch_tools;
	}

	public void setDispatch_tools(String dispatch_tools) {
		this.dispatch_tools = dispatch_tools;
	}

	public String getDispatch_expense() {
		return dispatch_expense;
	}

	public void setDispatch_expense(String dispatch_expense) {
		this.dispatch_expense = dispatch_expense;
	}

	public Double getMaterial_cost() {
		return material_cost;
	}

	public void setMaterial_cost(Double material_cost) {
		this.material_cost = material_cost;
	}

	public Double getLabor_cost() {
		return labor_cost;
	}

	public void setLabor_cost(Double labor_cost) {
		this.labor_cost = labor_cost;
	}

	public String getDispatch_evaluate() {
		return dispatch_evaluate;
	}

	public void setDispatch_evaluate(String dispatch_evaluate) {
		this.dispatch_evaluate = dispatch_evaluate;
	}

	public Date getDispatch_visit_time() {
		return dispatch_visit_time;
	}

	public void setDispatch_visit_time(Date dispatch_visit_time) {
		this.dispatch_visit_time = dispatch_visit_time;
	}
	@Column(name = "finish_img_url")
	public String getFinishImgUrl() {
		return finishImgUrl;
	}

	public void setFinishImgUrl(String finishImgUrl) {
		this.finishImgUrl = finishImgUrl;
	}
	@Column(name = "reject_reason")
	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	

}
