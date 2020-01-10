package com.jdry.pms.basicInfo.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_building_property")
public class BuildingProperty {

	private String storiedBuildId;
	private String storiedBuildName;
	private String belongCommId;
	private String storiedType;
	private String buildFloors;
	private String houseTypeId;
	private String remark;
	private Integer lzId;

	@Id
	@Column(name = "storied_build_id")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
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

	@Column(name = "build_floors")
	public String getBuildFloors() {
		return this.buildFloors;
	}

	public void setBuildFloors(String buildFloors) {
		this.buildFloors = buildFloors;
	}

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "house_type_id")
	public String getHouseTypeId() {
		return houseTypeId;
	}

	public void setHouseTypeId(String houseTypeId) {
		this.houseTypeId = houseTypeId;
	}

	@Column(name = "lz_id")
	public Integer getLzId() {
		return lzId;
	}

	public void setLzId(Integer lzId) {
		this.lzId = lzId;
	}

}
