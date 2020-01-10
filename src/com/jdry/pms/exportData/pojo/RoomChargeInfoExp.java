package com.jdry.pms.exportData.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "v_room_charge_info_exp")
public class RoomChargeInfoExp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String roomId;
	private String communityName;
	private String storiedBuildName;
	private String roomNo;
	private String roomType;
	private String ownerId;
	private String ownerName;
	private String phone;
	private String emergencyContact;
	private String emergencyContactPhone;
	private BigDecimal withinArea;
	private BigDecimal buildArea;
	private BigDecimal totalPrice;
	private Date receiveRoomDate;
	private Date makeRoomDate;
	private String roomDtate;
	private BigDecimal chargePrice;
	private String chargeType;
	private String chargeTypeNo;
	private String chargeTypeName;
	private BigDecimal amount;
	private String typeFlag;
	private BigDecimal arrearageAmount;
	private int arrearageNum;
	private String cardId;
	private BigDecimal serialAmount;

	@Id
	@Column(name = "room_id")
	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	@Column(name = "community_name")
	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	@Column(name = "storied_build_name")
	public String getStoriedBuildName() {
		return storiedBuildName;
	}

	public void setStoriedBuildName(String storiedBuildName) {
		this.storiedBuildName = storiedBuildName;
	}

	@Column(name = "room_no")
	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	@Column(name = "room_type")
	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	@Id
	@Column(name = "owner_id")
	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	@Column(name = "owner_name")
	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "emergency_contact")
	public String getEmergencyContact() {
		return emergencyContact;
	}

	public void setEmergencyContact(String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}

	@Column(name = "emergency_contact_phone")
	public String getEmergencyContactPhone() {
		return emergencyContactPhone;
	}

	public void setEmergencyContactPhone(String emergencyContactPhone) {
		this.emergencyContactPhone = emergencyContactPhone;
	}

	@Column(name = "within_area")
	public BigDecimal getWithinArea() {
		return withinArea;
	}

	public void setWithinArea(BigDecimal withinArea) {
		this.withinArea = withinArea;
	}

	@Column(name = "build_area")
	public BigDecimal getBuildArea() {
		return buildArea;
	}

	public void setBuildArea(BigDecimal buildArea) {
		this.buildArea = buildArea;
	}

	@Column(name = "total_price")
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Column(name = "receive_room_date")
	public Date getReceiveRoomDate() {
		return receiveRoomDate;
	}

	public void setReceiveRoomDate(Date receiveRoomDate) {
		this.receiveRoomDate = receiveRoomDate;
	}

	@Column(name = "make_room_date")
	public Date getMakeRoomDate() {
		return makeRoomDate;
	}

	public void setMakeRoomDate(Date makeRoomDate) {
		this.makeRoomDate = makeRoomDate;
	}

	@Column(name = "room_state")
	public String getRoomDtate() {
		return roomDtate;
	}

	public void setRoomDtate(String roomDtate) {
		this.roomDtate = roomDtate;
	}

	@Column(name = "charge_price")
	public BigDecimal getChargePrice() {
		return chargePrice;
	}

	public void setChargePrice(BigDecimal chargePrice) {
		this.chargePrice = chargePrice;
	}

	@Column(name = "charge_type")
	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	@Column(name = "charge_type_no")
	public String getChargeTypeNo() {
		return chargeTypeNo;
	}

	public void setChargeTypeNo(String chargeTypeNo) {
		this.chargeTypeNo = chargeTypeNo;
	}

	@Column(name = "charge_type_name")
	public String getChargeTypeName() {
		return chargeTypeName;
	}

	public void setChargeTypeName(String chargeTypeName) {
		this.chargeTypeName = chargeTypeName;
	}

	@Column(name = "amount")
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Column(name = "type_flag")
	public String getTypeFlag() {
		return typeFlag;
	}

	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}

	@Column(name = "arrearage_amount")
	public BigDecimal getArrearageAmount() {
		return arrearageAmount;
	}

	public void setArrearageAmount(BigDecimal arrearageAmount) {
		this.arrearageAmount = arrearageAmount;
	}

	@Column(name = "arrearage_num")
	public int getArrearageNum() {
		return arrearageNum;
	}

	public void setArrearageNum(int arrearageNum) {
		this.arrearageNum = arrearageNum;
	}

	@Column(name = "card_id")
	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	@Column(name = "serial_amount")
	public BigDecimal getSerialAmount() {
		return serialAmount;
	}

	public void setSerialAmount(BigDecimal serialAmount) {
		this.serialAmount = serialAmount;
	}

}
