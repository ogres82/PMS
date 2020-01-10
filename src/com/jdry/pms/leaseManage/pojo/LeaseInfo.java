package com.jdry.pms.leaseManage.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_lease_manage")
public class LeaseInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String leaseId;		//主键ID
	public String roomId;		//房间ID
	public String lesseesName;	//租客姓名
	public String phone;		//电话
	public String cardId;		//身份证
	public int monthsNum;		//出租月份
	public int monthsPrice;	//出租价格（单位：元）
	public Date startTime;		//开始时间
	public String state;		//状态（0：无效；1：有效）
	public String remark;		//备注
	public Date insertTime;	//创建时间
	public Date updateTime;	//更新时间
	public String operId;		//操作员ID
	public String deleteIs;	//逻辑删除（0：未删除；1：删除）
	
	@Id
	@Column(name = "lease_id")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getLeaseId() {
		return leaseId;
	}
	public void setLeaseId(String leaseId) {
		this.leaseId = leaseId;
	}

	@Column(name = "room_id")
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	@Column(name = "lessees_name")
	public String getLesseesName() {
		return lesseesName;
	}
	public void setLesseesName(String lesseesName) {
		this.lesseesName = lesseesName;
	}
	
	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}	

	@Column(name = "card_id")
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
	@Column(name = "months_num")
	public int getMonthsNum() {
		return monthsNum;
	}
	public void setMonthsNum(int monthsNum) {
		this.monthsNum = monthsNum;
	}
	
	@Column(name = "months_price")
	public int getMonthsPrice() {
		return monthsPrice;
	}
	public void setMonthsPrice(int monthsPrice) {
		this.monthsPrice = monthsPrice;
	}
	
	@Column(name = "start_time")
	@Temporal(TemporalType.DATE)
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "state")
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "insert_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Column(name = "oper_id")
	public String getOperId() {
		return operId;
	}
	public void setOperId(String operId) {
		this.operId = operId;
	}
	
	@Column(name = "delete_is")
	public String getDeleteIs() {
		return deleteIs;
	}
	public void setDeleteIs(String deleteIs) {
		this.deleteIs = deleteIs;
	}
}
