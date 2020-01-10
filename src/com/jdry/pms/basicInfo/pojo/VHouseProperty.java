package com.jdry.pms.basicInfo.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 房产信息表
 * 
 * @author Buxiaochao
 *
 */
@Entity
@Table(name = "v_t_house_property")
public class VHouseProperty {

	private String roomId;
	private String roomNo;
	private String belongSbId;
	private String houseType;
	private Float buildArea;
	private Float withinArea;
	private String roomType;
	private String roomState;
	private String chargeObject;
	private String remark;
	private Float advanceAmount;
	private Date makeRoomDate;
	private Date decorateStartDate;
	private Date decorateEndDate;
	private Date receiveRoomDate;
	private String build_id;
	private String build_name;
	private String community_id;
	private String community_name;
	private String storied_build_name;
	private String room_type_name;
	private String room_state_name;
	private String charge_object_name;
	private String unitId;
	private String unitName;
	private Integer lzId;	


	@Id
	@Column(name = "room_id")
	public String getRoomId() {
		return this.roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	@Column(name = "room_no")
	public String getRoomNo() {
		return this.roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	@Column(name = "belong_sb_id")
	public String getBelongSbId() {
		return this.belongSbId;
	}

	public void setBelongSbId(String belongSbId) {
		this.belongSbId = belongSbId;
	}

	@Column(name = "house_type")
	public String getHouseType() {
		return this.houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

	@Column(name = "build_area")
	public Float getBuildArea() {
		return this.buildArea;
	}

	public void setBuildArea(Float buildArea) {
		this.buildArea = buildArea;
	}

	@Column(name = "within_area")
	public Float getWithinArea() {
		return this.withinArea;
	}

	public void setWithinArea(Float withinArea) {
		this.withinArea = withinArea;
	}

	@Column(name = "room_type")
	public String getRoomType() {
		return this.roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	@Column(name = "room_state")
	public String getRoomState() {
		return this.roomState;
	}

	public void setRoomState(String roomState) {
		this.roomState = roomState;
	}

	@Column(name = "charge_object")
	public String getChargeObject() {
		return this.chargeObject;
	}

	public void setChargeObject(String chargeObject) {
		this.chargeObject = chargeObject;
	}

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "build_name")
	public String getBuild_name() {
		return build_name;
	}

	public void setBuild_name(String build_name) {
		this.build_name = build_name;
	}

	@Column(name = "community_name")
	public String getCommunity_name() {
		return community_name;
	}

	public void setCommunity_name(String community_name) {
		this.community_name = community_name;
	}

	@Column(name = "storied_build_name")
	public String getStoried_build_name() {
		return storied_build_name;
	}

	public void setStoried_build_name(String storied_build_name) {
		this.storied_build_name = storied_build_name;
	}

	@Column(name = "room_type_name")
	public String getRoom_type_name() {
		return room_type_name;
	}

	public void setRoom_type_name(String room_type_name) {
		this.room_type_name = room_type_name;
	}

	@Column(name = "room_state_name")
	public String getRoom_state_name() {
		return room_state_name;
	}

	public void setRoom_state_name(String room_state_name) {
		this.room_state_name = room_state_name;
	}

	@Column(name = "charge_object_name")
	public String getCharge_object_name() {
		return charge_object_name;
	}

	public void setCharge_object_name(String charge_object_name) {
		this.charge_object_name = charge_object_name;
	}

	@Column(name = "build_id")
	public String getBuild_id() {
		return build_id;
	}

	public void setBuild_id(String build_id) {
		this.build_id = build_id;
	}

	@Column(name = "community_id")
	public String getCommunity_id() {
		return community_id;
	}

	public void setCommunity_id(String community_id) {
		this.community_id = community_id;
	}

	@Column(name = "advance_amount")
	public Float getAdvanceAmount() {
		return this.advanceAmount;
	}

	public void setAdvanceAmount(Float advanceAmount) {
		this.advanceAmount = advanceAmount;
	}

	@Column(name = "make_room_date")
	public Date getMakeRoomDate() {
		return this.makeRoomDate;
	}

	public void setMakeRoomDate(Date makeRoomDate) {
		this.makeRoomDate = makeRoomDate;
	}

	@Column(name = "decorate_start_date")
	public Date getDecorateStartDate() {
		return this.decorateStartDate;
	}

	public void setDecorateStartDate(Date decorateStartDate) {
		this.decorateStartDate = decorateStartDate;
	}

	@Column(name = "decorate_end_date")
	public Date getDecorateEndDate() {
		return this.decorateEndDate;
	}

	public void setDecorateEndDate(Date decorateEndDate) {
		this.decorateEndDate = decorateEndDate;
	}

	@Column(name = "receive_room_date")
	public Date getReceiveRoomDate() {
		return receiveRoomDate;
	}

	public void setReceiveRoomDate(Date receiveRoomDate) {
		this.receiveRoomDate = receiveRoomDate;
	}

	@Column(name = "lz_id")
	public Integer getLzId() {
		return lzId;
	}

	public void setLzId(Integer lzId) {
		this.lzId = lzId;
	}

	@Column(name = "unit_id")
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	@Column(name = "unit_name")
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}



}
