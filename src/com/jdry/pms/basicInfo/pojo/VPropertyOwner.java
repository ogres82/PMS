package com.jdry.pms.basicInfo.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "v_t_property_owner")
public class VPropertyOwner {

	private String ownerId;
	private String ownerName;
	private String phone;
	private String telPhone;
	private String password;
	private String cardId;
	private Date birthDate;
	private String carId;
	private String ownerType;
	private String ownerIdentity;
	private String parentId;
	private String owner_type_name;
	private String owner_identity_name;
	private String remark;
	private String ownerHkId;
	
	private String sex;
	private String ownerHeadImg;
	private String ownerAddrs;
	private String emergencyContact;
	private String emergencyContactPhone;
	


	@Id
	@Column(name = "owner_id")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getOwnerId() {
		return this.ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	@Column(name = "owner_name")
	public String getOwnerName() {
		return this.ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	@Column(name = "phone")
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "tel_phone")
	public String getTelPhone() {
		return this.telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	@Column(name = "password")
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "card_id")
	public String getCardId() {
		return this.cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	@Column(name = "birth_date")
	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Column(name = "car_id")
	public String getCarId() {
		return this.carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	@Column(name = "owner_type")
	public String getOwnerType() {
		return this.ownerType;
	}

	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}

	@Column(name = "owner_identity")
	public String getOwnerIdentity() {
		return this.ownerIdentity;
	}

	public void setOwnerIdentity(String ownerIdentity) {
		this.ownerIdentity = ownerIdentity;
	}

	@Column(name = "parent_id")
	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "owner_type_name")
	public String getOwner_type_name() {
		return owner_type_name;
	}

	public void setOwner_type_name(String owner_type_name) {
		this.owner_type_name = owner_type_name;
	}

	@Column(name = "owner_identity_name")
	public String getOwner_identity_name() {
		return owner_identity_name;
	}

	public void setOwner_identity_name(String owner_identity_name) {
		this.owner_identity_name = owner_identity_name;
	}

	@Column(name = "owner_hk_id")
	public String getOwnerHkId() {
		return this.ownerHkId;
	}

	public void setOwnerHkId(String ownerHkId) {
		this.ownerHkId = ownerHkId;
	}
	@Column(name = "owner_head_img")
	public String getOwnerHeadImg() {
		return ownerHeadImg;
	}

	public void setOwnerHeadImg(String ownerHeadImg) {
		this.ownerHeadImg = ownerHeadImg;
	}

	@Column(name = "sex")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	

	@Formula(value="(SELECT group_concat(CONCAT(t.community_name,(case t.room_type_name when '别墅' then ''else t.storied_build_name end ),t.room_no) ) FROM v_house_vs_owner t where t.owner_id = owner_id)")
	public String getOwnerAddrs() {
		return ownerAddrs;
	}

	public void setOwnerAddrs(String ownerAddrs) {
		this.ownerAddrs = ownerAddrs;
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
}
