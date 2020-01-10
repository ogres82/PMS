package com.jdry.pms.basicInfo.pojo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_house_property")
public class HouseProperty {

	private String roomId;
	private String buildId;
	private String buildName;
	private String communityId;
	private String communityName;
	private String belongSbId;
	private String storiedBuildName;
	private String roomNo;
	private String houseType;
	private BigDecimal buildArea;
	private BigDecimal withinArea;
	private String roomType;
	private String roomState;
	private String chargeObject;
	private String remark;
	private BigDecimal advanceAmount;
	private Date makeRoomDate;
	private Date decorateStartDate;
	private Date decorateEndDate;
	private Date receiveRoomDate;
	private Date decoratePlanDate;
	private String roomDecorateStatus;
	private String decorateInstructions;

	private String unitId;
	private String unitName;
	private Integer lzId;
	private String isDel="0";
	private Date updateTime = new Date();
	

	

	@Id
	@Column(name = "room_id")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")

	public String getRoomId() {
		return this.roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	@Column(name = "build_id")
	public String getBuildId() {
		return this.buildId;
	}

	public void setBuildId(String buildId) {
		this.buildId = buildId;
	}

	@Column(name = "build_name")
	public String getBuildName() {
		return this.buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	@Column(name = "community_id")
	public String getCommunityId() {
		return this.communityId;
	}

	public void setCommunityId(String communityId) {
		this.communityId = communityId;
	}

	@Column(name = "community_name")
	public String getCommunityName() {
		return this.communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	@Column(name = "belong_sb_id")
	public String getBelongSbId() {
		return this.belongSbId;
	}

	public void setBelongSbId(String belongSbId) {
		this.belongSbId = belongSbId;
	}

	@Column(name = "storied_build_name")
	public String getStoriedBuildName() {
		return this.storiedBuildName;
	}

	public void setStoriedBuildName(String storiedBuildName) {
		this.storiedBuildName = storiedBuildName;
	}

	@Column(name = "room_no")
	public String getRoomNo() {
		return this.roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	@Column(name = "house_type")
	public String getHouseType() {
		return this.houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

	@Column(name = "build_area")
	public BigDecimal getBuildArea() {
		return this.buildArea;
	}

	public void setBuildArea(BigDecimal buildArea) {
		this.buildArea = buildArea;
	}

	@Column(name = "within_area")
	public BigDecimal getWithinArea() {
		return this.withinArea;
	}

	public void setWithinArea(BigDecimal withinArea) {
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

	@Column(name = "advance_amount")
	public BigDecimal getAdvanceAmount() {
		return this.advanceAmount;
	}

	public void setAdvanceAmount(BigDecimal advanceAmount) {
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
		return this.receiveRoomDate;
	}

	public void setReceiveRoomDate(Date receiveRoomDate) {
		this.receiveRoomDate = receiveRoomDate;
	}

	@Column(name = "decorate_plan_date")
	public Date getDecoratePlanDate() {
		return this.decoratePlanDate;
	}

	public void setDecoratePlanDate(Date decoratePlanDate) {
		this.decoratePlanDate = decoratePlanDate;
	}

	@Column(name = "room_decorate_status")
	public String getRoomDecorateStatus() {
		return this.roomDecorateStatus;
	}

	public void setRoomDecorateStatus(String roomDecorateStatus) {
		this.roomDecorateStatus = roomDecorateStatus;
	}

	@Column(name = "decorate_instructions")
	public String getDecorateInstructions() {
		return this.decorateInstructions;
	}

	public void setDecorateInstructions(String decorateInstructions) {
		this.decorateInstructions = decorateInstructions;
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

	@Column(name = "is_del")
	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	@Column(name = "update_time")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "unit_name")
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	
}
