package com.jdry.pms.basicInfo.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "v_t_building_property")
public class VBuildingProperty {

	private String storiedBuildId;
	private String storiedBuildName;
	private Integer lzId;
	private String belongCommId;
	private String community_name;
	private Integer lzCommId;
	private String storiedType;
	private String storiedTypeName;
	private String remark;
	private String build_id;
	private String build_name;
	private Integer lzBuildId;
	private String floors;
	private String houseTypeId;
	private String houseTypeName;

	@Id
	@Column(name = "storied_build_id")
	public String getStoriedBuildId() {
		return this.storiedBuildId;
	}

	public void setStoriedBuildId(String storiedBuildId) {
		this.storiedBuildId = storiedBuildId;
	}

	@Column(name = "storied_build_name")
	public String getStoriedBuildName() {
		return this.storiedBuildName;
	}

	public void setStoriedBuildName(String storiedBuildName) {
		this.storiedBuildName = storiedBuildName;
	}

	@Column(name = "belong_comm_id")
	public String getBelongCommId() {
		return this.belongCommId;
	}

	public void setBelongCommId(String belongCommId) {
		this.belongCommId = belongCommId;
	}

	@Column(name = "storied_type")
	public String getStoriedType() {
		return this.storiedType;
	}

	public void setStoriedType(String storiedType) {
		this.storiedType = storiedType;
	}

	@Column(name = "storied_type_name")
	public String getStoriedTypeName() {
		return storiedTypeName;
	}

	public void setStoriedTypeName(String storiedTypeName) {
		this.storiedTypeName = storiedTypeName;
	}

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "community_name")
	public String getCommunity_name() {
		return community_name;
	}

	public void setCommunity_name(String community_name) {
		this.community_name = community_name;
	}

	@Column(name = "build_id")
	public String getBuild_id() {
		return build_id;
	}

	public void setBuild_id(String build_id) {
		this.build_id = build_id;
	}

	@Column(name = "build_name")
	public String getBuild_name() {
		return build_name;
	}

	public void setBuild_name(String build_name) {
		this.build_name = build_name;
	}

	@Column(name = "build_floors")
	public String getFloors() {
		return floors;
	}

	public void setFloors(String floors) {
		this.floors = floors;
	}

	@Column(name = "house_type_id")
	public String getHouseTypeId() {
		return houseTypeId;
	}

	public void setHouseTypeId(String houseTypeId) {
		this.houseTypeId = houseTypeId;
	}

	@Column(name = "house_type_name")
	public String getHouseTypeName() {
		return houseTypeName;
	}

	public void setHouseTypeName(String houseTypeName) {
		this.houseTypeName = houseTypeName;
	}

	@Column(name = "lz_id")
	public Integer getLzId() {
		return lzId;
	}

	public void setLzId(Integer lzId) {
		this.lzId = lzId;
	}

	@Column(name = "lz_build_id")
	public Integer getLzBuildId() {
		return lzBuildId;
	}

	public void setLzBuildId(Integer lzBuildId) {
		this.lzBuildId = lzBuildId;
	}

	@Column(name = "lz_comm_id")
	public Integer getLzCommId() {
		return lzCommId;
	}

	public void setLzCommId(Integer lzCommId) {
		this.lzCommId = lzCommId;
	}
}
