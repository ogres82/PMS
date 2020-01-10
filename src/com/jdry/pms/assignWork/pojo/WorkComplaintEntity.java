package com.jdry.pms.assignWork.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_R_MAINTAIN_COMPLAINT")
public class WorkComplaintEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6993643149444467445L;
	private String comp_kid;// key主键
	private String comp_id;// 投诉单号
	private String mtn_id;// 报修单号
	private String comp_detail;// 详细描述
	private String comp_status;// 投诉状态
	private String comp_operator;// 受理人
	private String comp_operator_id;// 处理人id
	private String comp_emergency;// 紧急程度 varchar2(5)
	private String comp_createby;// 新增人 varchar2(5)
	private Date comp_createTime;// 新增时间 varchar2(5)
	private String comp_process;// 处理过程
	private String comp_result;// 处理结果
	private Date comp_finish_time;// 销单时间
	private String comp_degree;// 满意度
	private String comp_solve;// 是否解决
	private String comp_visit_lev;// 回访业主评级
	private String comp_visit_record;// 回访记录
	private String comp_visit_recording;// 回访记录录音
	private Date finish_time;// 完成时间
	private String comp_reply;
	public String getComp_reply() {
		return comp_reply;
	}

	public void setComp_reply(String comp_reply) {
		this.comp_reply = comp_reply;
	}



	public Date getFinish_time() {
		return finish_time;
	}

	public void setFinish_time(Date finish_time) {
		this.finish_time = finish_time;
	}

	@Id
	public String getComp_kid() {
		return comp_kid;
	}

	public void setComp_kid(String comp_kid) {
		this.comp_kid = comp_kid;
	}

	public String getComp_id() {
		return comp_id;
	}

	public void setComp_id(String comp_id) {
		this.comp_id = comp_id;
	}

	public String getMtn_id() {
		return mtn_id;
	}

	public void setMtn_id(String mtn_id) {
		this.mtn_id = mtn_id;
	}

	public String getComp_detail() {
		return comp_detail;
	}

	public void setComp_detail(String comp_detail) {
		this.comp_detail = comp_detail;
	}

	public String getComp_status() {
		return comp_status;
	}

	public void setComp_status(String comp_status) {
		this.comp_status = comp_status;
	}

	public String getComp_operator() {
		return comp_operator;
	}

	public void setComp_operator(String comp_operator) {
		this.comp_operator = comp_operator;
	}

	public String getComp_operator_id() {
		return comp_operator_id;
	}

	public void setComp_operator_id(String comp_operator_id) {
		this.comp_operator_id = comp_operator_id;
	}

	public String getComp_emergency() {
		return comp_emergency;
	}

	public void setComp_emergency(String comp_emergency) {
		this.comp_emergency = comp_emergency;
	}

	public String getComp_process() {
		return comp_process;
	}

	public String getComp_createby() {
		return comp_createby;
	}

	public void setComp_createby(String comp_createby) {
		this.comp_createby = comp_createby;
	}

	public Date getComp_createTime() {
		return comp_createTime;
	}

	public void setComp_createTime(Date comp_createTime) {
		this.comp_createTime = comp_createTime;
	}

	public void setComp_process(String comp_process) {
		this.comp_process = comp_process;
	}

	public String getComp_result() {
		return comp_result;
	}

	public void setComp_result(String comp_result) {
		this.comp_result = comp_result;
	}

	public Date getComp_finish_time() {
		return comp_finish_time;
	}

	public void setComp_finish_time(Date comp_finish_time) {
		this.comp_finish_time = comp_finish_time;
	}

	public String getComp_degree() {
		return comp_degree;
	}

	public void setComp_degree(String comp_degree) {
		this.comp_degree = comp_degree;
	}

	public String getComp_solve() {
		return comp_solve;
	}

	public void setComp_solve(String comp_solve) {
		this.comp_solve = comp_solve;
	}

	public String getComp_visit_lev() {
		return comp_visit_lev;
	}

	public void setComp_visit_lev(String comp_visit_lev) {
		this.comp_visit_lev = comp_visit_lev;
	}

	public String getComp_visit_record() {
		return comp_visit_record;
	}

	public void setComp_visit_record(String comp_visit_record) {
		this.comp_visit_record = comp_visit_record;
	}

	public String getComp_visit_recording() {
		return comp_visit_recording;
	}

	public void setComp_visit_recording(String comp_visit_recording) {
		this.comp_visit_recording = comp_visit_recording;
	}

}
