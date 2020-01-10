package com.jdry.pms.equipment.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 设备保养记录
 * @author xulei
 *
 */
@Entity
@Table(name = "t_eqp_fix_rec")
public class EqpFixRec {

	private String fixRecId;
	private String eqpId;
	private Date eqpFixDate;
	private Date eqpFixEnddate;
	private String eqpFixPerson;
	private String eqpFixContent;
	private float eqpFixFee;
	private float eqpFixTime;
	private String eqpFixDetail;
	private String eqpHandlRes;
	
	
	
	@Id
	@Column(name = "fix_rec_id")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getFixRecId() {
		return fixRecId;
	}
	public void setFixRecId(String fixRecId) {
		this.fixRecId = fixRecId;
	}
	@Column(name = "eqp_id")
	public String getEqpId() {
		return eqpId;
	}
	public void setEqpId(String eqpId) {
		this.eqpId = eqpId;
	}
	@Column(name = "eqp_fix_date")
	public Date getEqpFixDate() {
		return eqpFixDate;
	}
	public void setEqpFixDate(Date eqpFixDate) {
		this.eqpFixDate = eqpFixDate;
	}
	@Column(name = "eqp_fix_endDate")
	public Date getEqpFixEnddate() {
		return eqpFixEnddate;
	}
	public void setEqpFixEnddate(Date eqpFixEnddate) {
		this.eqpFixEnddate = eqpFixEnddate;
	}
	@Column(name = "eqp_fix_person")
	public String getEqpFixPerson() {
		return eqpFixPerson;
	}
	public void setEqpFixPerson(String eqpFixPerson) {
		this.eqpFixPerson = eqpFixPerson;
	}
	@Column(name = "eqp_fix_content")
	public String getEqpFixContent() {
		return eqpFixContent;
	}
	public void setEqpFixContent(String eqpFixContent) {
		this.eqpFixContent = eqpFixContent;
	}
	@Column(name = "eqp_fix_fee")
	public float getEqpFixFee() {
		return eqpFixFee;
	}
	public void setEqpFixFee(float eqpFixFee) {
		this.eqpFixFee = eqpFixFee;
	}
	@Column(name = "eqp_fix_time")
	public float getEqpFixTime() {
		return eqpFixTime;
	}
	public void setEqpFixTime(float eqpFixTime) {
		this.eqpFixTime = eqpFixTime;
	}
	@Column(name = "eqp_fix_detail")
	public String getEqpFixDetail() {
		return eqpFixDetail;
	}
	public void setEqpFixDetail(String eqpFixDetail) {
		this.eqpFixDetail = eqpFixDetail;
	}
	@Column(name = "eqp_handl_res")
	public String getEqpHandlRes() {
		return eqpHandlRes;
	}
	public void setEqpHandlRes(String eqpHandlRes) {
		this.eqpHandlRes = eqpHandlRes;
	}
	
	
}
