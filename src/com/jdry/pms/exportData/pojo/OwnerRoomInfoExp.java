package com.jdry.pms.exportData.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "v_owner_room_info_exp")
public class OwnerRoomInfoExp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String ownerId;
	private String roomId;
	private String communityId;
	private String belongSbId;
	private String ownerName;
	private String phone;
	private String cardId;
	private String carId;
	private String emergencyContact;
	private String emergencyContactPhone;
	private String communityName;
	private String storiedBuildName;
	private String roomNo;
	private String roomState;
	private String roomType;
	private BigDecimal buildArea;
	private BigDecimal withinArea;
	private Date makeRoomDate;
	private Date receiveRoomDate;

	@Id
	@Column(name="owner_id")
	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	@Id
	@Column(name="room_id")
	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	@Column(name="community_id")
	public String getCommunityId() {
		return communityId;
	}

	public void setCommunityId(String communityId) {
		this.communityId = communityId;
	}

	@Column(name="belong_sb_id")
	public String getBelongSbId() {
		return belongSbId;
	}

	public void setBelongSbId(String belongSbId) {
		this.belongSbId = belongSbId;
	}

	@Column(name="owner_name")
	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	@Column(name="phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name="card_id")
	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	@Column(name="car_id")
	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	@Column(name="emergency_contact")
	public String getEmergencyContact() {
		return emergencyContact;
	}

	public void setEmergencyContact(String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}

	@Column(name="emergency_contact_phone")
	public String getEmergencyContactPhone() {
		return emergencyContactPhone;
	}

	public void setEmergencyContactPhone(String emergencyContactPhone) {
		this.emergencyContactPhone = emergencyContactPhone;
	}

	@Column(name="community_name")
	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	@Column(name="storied_build_name")
	public String getStoriedBuildName() {
		return storiedBuildName;
	}

	public void setStoriedBuildName(String storiedBuildName) {
		this.storiedBuildName = storiedBuildName;
	}

	@Column(name="room_no")
	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	@Column(name="room_state")
	public String getRoomState() {
		return roomState;
	}

	public void setRoomState(String roomState) {
		this.roomState = roomState;
	}

	@Column(name="room_type")
	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	@Column(name="build_area")
	public BigDecimal getBuildArea() {
		return buildArea;
	}

	public void setBuildArea(BigDecimal buildArea) {
		this.buildArea = buildArea;
	}

	@Column(name="within_area")
	public BigDecimal getWithinArea() {
		return withinArea;
	}

	public void setWithinArea(BigDecimal withinArea) {
		this.withinArea = withinArea;
	}

	@Column(name="make_room_date")
	public Date getMakeRoomDate() {
		return makeRoomDate;
	}

	public void setMakeRoomDate(Date makeRoomDate) {
		this.makeRoomDate = makeRoomDate;
	}

	@Column(name="receive_room_date")
	public Date getReceiveRoomDate() {
		return receiveRoomDate;
	}

	public void setReceiveRoomDate(Date receiveRoomDate) {
		this.receiveRoomDate = receiveRoomDate;
	}

}
