package com.jdry.pms.cleaning.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

//@Entity
//@Table(name = "t_clean_rec")
public class CleanRec {
	
	String recId;
	String areaId;
	String cleanType;
	String cleanerId;
	Date workTime;
	String workPlace;
	String workContent;
	int isFinish;
	String checkerId;
	String checkerSign;
	String remark;
	
	@Id
	@Column(name = "REC_ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getRecId() {
		return recId;
	}
	public void setRecId(String recId) {
		this.recId = recId;
	}
	@Column(name = "AREA_ID")
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	@Column(name = "CLEAN_TYPE")
	public String getCleanType() {
		return cleanType;
	}
	public void setCleanType(String cleanType) {
		this.cleanType = cleanType;
	}
	@Column(name = "CLEANER_ID")
	public String getCleanerId() {
		return cleanerId;
	}
	public void setCleanerId(String cleanerId) {
		this.cleanerId = cleanerId;
	}
	@Column(name = "WORK_TIME")
	public Date getWorkTime() {
		return workTime;
	}
	public void setWorkTime(Date workTime) {
		this.workTime = workTime;
	}
	@Column(name = "WORK_PLACE")
	public String getWorkPlace() {
		return workPlace;
	}
	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}
	@Column(name = "WORK_CONTENT")
	public String getWorkContent() {
		return workContent;
	}
	public void setWorkContent(String workContent) {
		this.workContent = workContent;
	}
	@Column(name = "IS_FINISH")
	public int isFinish() {
		return isFinish;
	}
	public void setFinish(int isFinish) {
		this.isFinish = isFinish;
	}
	@Column(name = "CHECKER_ID")
	public String getCheckerId() {
		return checkerId;
	}
	public void setCheckerId(String checkerId) {
		this.checkerId = checkerId;
	}
	@Column(name = "CHECKER_SIGN")
	public String getCheckerSign() {
		return checkerSign;
	}
	public void setCheckerSign(String checkerSign) {
		this.checkerSign = checkerSign;
	}
	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
