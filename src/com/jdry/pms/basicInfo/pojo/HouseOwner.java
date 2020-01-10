package com.jdry.pms.basicInfo.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_house_owner")
public class HouseOwner {

	private String id;
	private String roomId;
	private String ownerId;
	private String defaultMark;
	private String bandingMark;
	private Integer lzId;
	private String isDel = "0";
	private String remark;
	private String operId;
	private Date updateTime = new Date();



	@Column(name = "is_del")
	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "oper_id")
	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}

	@Column(name = "update_time")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "room_id")
	public String getRoomId() {
		return this.roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	@Column(name = "owner_id")
	public String getOwnerId() {
		return this.ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	@Column(name = "default_mark")
	public String getDefaultMark() {
		return this.defaultMark;
	}

	public void setDefaultMark(String defaultMark) {
		this.defaultMark = defaultMark;
	}

	@Column(name = "banding_mark")
	public String getBandingMark() {
		return bandingMark;
	}

	public void setBandingMark(String bandingMark) {
		this.bandingMark = bandingMark;
	}

	@Column(name = "lz_id")
	public Integer getLzId() {
		return lzId;
	}

	public void setLzId(Integer lzId) {
		this.lzId = lzId;
	}

}
