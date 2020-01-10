package com.jdry.pms.assignWork.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "v_r_t_assign_comp")
public class VWorkCompEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2555175752940598343L;
	private String mtn_id;
	private String comp_kid;
	private String rpt_name;
	private String addres;
	private String createTime;
	private String finishTime;
	private String comp_emergency;
	private String comp_emergency_name;
	private String comp_status;
	private String comp_status_name;
	private String comp_finish_time;
	private String cname;
	private String comp_id;
	private String comp_operator_name;
	private String comp_operator_id;
	
	
	public String getComp_id() {
		return comp_id;
	}
	public void setComp_id(String comp_id) {
		this.comp_id = comp_id;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	@Id
	public String getMtn_id() {
		return mtn_id;
	}
	public void setMtn_id(String mtn_id) {
		this.mtn_id = mtn_id;
	}
	public String getComp_kid() {
		return comp_kid;
	}
	public void setComp_kid(String comp_kid) {
		this.comp_kid = comp_kid;
	}
	public String getRpt_name() {
		return rpt_name;
	}
	public void setRpt_name(String rpt_name) {
		this.rpt_name = rpt_name;
	}
	public String getAddres() {
		return addres;
	}
	public void setAddres(String addres) {
		this.addres = addres;
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
	public String getComp_emergency() {
		return comp_emergency;
	}
	public void setComp_emergency(String comp_emergency) {
		this.comp_emergency = comp_emergency;
	}
	public String getComp_emergency_name() {
		return comp_emergency_name;
	}
	public void setComp_emergency_name(String comp_emergency_name) {
		this.comp_emergency_name = comp_emergency_name;
	}
	public String getComp_status() {
		return comp_status;
	}
	public void setComp_status(String comp_status) {
		this.comp_status = comp_status;
	}
	public String getComp_status_name() {
		return comp_status_name;
	}
	public void setComp_status_name(String comp_status_name) {
		this.comp_status_name = comp_status_name;
	}
	public String getComp_finish_time() {
		return comp_finish_time;
	}
	public void setComp_finish_time(String comp_finish_time) {
		this.comp_finish_time = comp_finish_time;
	}
	public String getComp_operator_name() {
		return comp_operator_name;
	}
	public void setComp_operator_name(String comp_operator_name) {
		this.comp_operator_name = comp_operator_name;
	}
	public String getComp_operator_id() {
		return comp_operator_id;
	}
	public void setComp_operator_id(String comp_operator_id) {
		this.comp_operator_id = comp_operator_id;
	}
	
	
}
