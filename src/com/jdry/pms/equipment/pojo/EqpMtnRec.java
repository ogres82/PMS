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
@Table(name = "t_eqp_mtn_rec")
public class EqpMtnRec {

	private String mtnRecId;
	private String eqpId;
	private String eqpMtnType;
	private String eqpMtnContent;
	private Date eqpMtnDate;
	private Date eqpMtnEnddate;
	private String eqpMtnPerson;
	private float eqpMtnTime;
	private float eqpMtnFee;
	private String eqpMtnDetail;
	
	@Id
	@Column(name = "mtn_rec_id")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getMtnRecId() {
		return mtnRecId;
	}
	public void setMtnRecId(String mtnRecId) {
		this.mtnRecId = mtnRecId;
	}
	@Column(name = "eqp_id")
	public String getEqpId() {
		return eqpId;
	}
	public void setEqpId(String eqpId) {
		this.eqpId = eqpId;
	}
	@Column(name = "eqp_mtn_type")
	public String getEqpMtnType() {
		return eqpMtnType;
	}
	public void setEqpMtnType(String eqpMtnType) {
		this.eqpMtnType = eqpMtnType;
	}
	@Column(name = "eqp_mtn_content")
	public String getEqpMtnContent() {
		return eqpMtnContent;
	}
	public void setEqpMtnContent(String eqpMtnContent) {
		this.eqpMtnContent = eqpMtnContent;
	}
	@Column(name = "eqp_mtn_date")
	public Date getEqpMtnDate() {
		return eqpMtnDate;
	}
	public void setEqpMtnDate(Date eqpMtnDate) {
		this.eqpMtnDate = eqpMtnDate;
	}
	@Column(name = "eqp_mtn_endDate")
	public Date getEqpMtnEnddate() {
		return eqpMtnEnddate;
	}
	public void setEqpMtnEnddate(Date eqpMtnEnddate) {
		this.eqpMtnEnddate = eqpMtnEnddate;
	}
	@Column(name = "eqp_mtn_person")
	public String getEqpMtnPerson() {
		return eqpMtnPerson;
	}
	public void setEqpMtnPerson(String eqpMtnPerson) {
		this.eqpMtnPerson = eqpMtnPerson;
	}
	@Column(name = "eqp_mtn_time")
	public float getEqpMtnTime() {
		return eqpMtnTime;
	}
	public void setEqpMtnTime(float eqpMtnTime) {
		this.eqpMtnTime = eqpMtnTime;
	}
	@Column(name = "eqp_mtn_fee")
	public float getEqpMtnFee() {
		return eqpMtnFee;
	}
	public void setEqpMtnFee(float eqpMtnFee) {
		this.eqpMtnFee = eqpMtnFee;
	}
	@Column(name = "eqp_mtn_detail")
	public String getEqpMtnDetail() {
		return eqpMtnDetail;
	}
	public void setEqpMtnDetail(String eqpMtnDetail) {
		this.eqpMtnDetail = eqpMtnDetail;
	}
	
	
	
	
}
