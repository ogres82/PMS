package com.jdry.pms.basicInfo.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "v_owner_room_basic_info")
public class VOwnerRoomBasicInfo {
	
	private String ownerId;
	private String ownerName;
	private String sex;
	private String phone;
	private String telPhone;
	private String cardId;
	private String birthDate;
	private String carId;
	private String ownerType;
	private String regState;
	private String ownerTypeName;
	private String ownerIdentity;
	private String ownerIdentityName;
	private String parentName;
	private String remark;
	private String ownerHeadImg;
	private String ownerHkId;
	private String emergencyContact;
	private String emergencyContactPhone;
	private String isDel;
	private String ownerAddrs;
	private String roomId;
	private Integer roomLzid;
	
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
	
	@Column(name = "sex")
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(name = "tel_phone")
	public String getTelPhone() {
		return telPhone;
	}
	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	
	@Column(name = "card_id")
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
	@Column(name = "birth_date")
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	
	@Column(name = "car_id")
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	
	@Column(name = "owner_type")
	public String getOwnerType() {
		return ownerType;
	}
	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}
	
	@Column(name = "reg_state")
	public String getRegState() {
		return regState;
	}
	public void setRegState(String regState) {
		this.regState = regState;
	}
	
	@Column(name = "owner_type_name")
	public String getOwnerTypeName() {
		return ownerTypeName;
	}
	public void setOwnerTypeName(String ownerTypeName) {
		this.ownerTypeName = ownerTypeName;
	}
	
	@Column(name = "owner_identity")
	public String getOwnerIdentity() {
		return ownerIdentity;
	}
	public void setOwnerIdentity(String ownerIdentity) {
		this.ownerIdentity = ownerIdentity;
	}
	
	@Column(name = "owner_identity_name")
	public String getOwnerIdentityName() {
		return ownerIdentityName;
	}
	public void setOwnerIdentityName(String ownerIdentityName) {
		this.ownerIdentityName = ownerIdentityName;
	}
	
	@Column(name = "parent_name")
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "owner_head_img")
	public String getOwnerHeadImg() {
		return ownerHeadImg;
	}
	public void setOwnerHeadImg(String ownerHeadImg) {
		this.ownerHeadImg = ownerHeadImg;
	}
	
	@Column(name = "owner_hk_id")
	public String getOwnerHkId() {
		return ownerHkId;
	}
	public void setOwnerHkId(String ownerHkId) {
		this.ownerHkId = ownerHkId;
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
	
	@Column(name = "is_del")
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	
	@Column(name = "room_addrs")
	public String getOwnerAddrs() {
		return ownerAddrs;
	}
	public void setOwnerAddrs(String ownerAddrs) {
		this.ownerAddrs = ownerAddrs;
	}	

	@Column(name = "room_id")
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	
	@Column(name = "room_lz_id")
	public Integer getRoomLzid() {
		return roomLzid;
	}
	public void setRoomLzid(Integer roomLzid) {
		this.roomLzid = roomLzid;
	}
	
}
