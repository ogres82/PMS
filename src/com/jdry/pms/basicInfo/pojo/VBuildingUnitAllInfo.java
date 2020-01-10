package com.jdry.pms.basicInfo.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "v_building_unit_all_info")
public class VBuildingUnitAllInfo  {
	private String buildid;
	private String buildname;
	private Integer buildLzId;	
	
	private String communityId;
	private String communityName;
	private Integer communityLzId;
	
	private String storiedBuildId;
	private String storiedBuildName;
	private Integer storiedBuildLzId;
	
	private String unitId;
	private String unitName;
	private Integer unitLzId;	
	
	private String isSycn;



	@Column(name = "build_id")
	public String getBuildid() {
		return buildid;
	}
	public void setBuildid(String buildid) {
		this.buildid = buildid;
	}
	

	@Column(name = "build_name")
	public String getBuildname() {
		return buildname;
	}
	public void setBuildname(String buildname) {
		this.buildname = buildname;
	}
	

	@Column(name = "build_lz_id")
	public Integer getBuildLzId() {
		return buildLzId;
	}
	public void setBuildLzId(Integer buildLzId) {
		this.buildLzId = buildLzId;
	}
	

	@Column(name = "community_id")
	public String getCommunityId() {
		return communityId;
	}
	public void setCommunityId(String communityId) {
		this.communityId = communityId;
	}
	
	@Column(name = "community_name")
	public String getCommunityName() {
		return communityName;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}	

	@Column(name = "community_lz_id")
	public Integer getCommunityLzId() {
		return communityLzId;
	}
	public void setCommunityLzId(Integer communityLzId) {
		this.communityLzId = communityLzId;
	}
	

	@Column(name = "storied_build_id")
	public String getStoriedBuildId() {
		return storiedBuildId;
	}
	public void setStoriedBuildId(String storiedBuildId) {
		this.storiedBuildId = storiedBuildId;
	}
	

	@Column(name = "storied_build_name")
	public String getStoriedBuildName() {
		return storiedBuildName;
	}
	public void setStoriedBuildName(String storiedBuildName) {
		this.storiedBuildName = storiedBuildName;
	}
	

	@Column(name = "storied_build_lz_id")
	public Integer getStoriedBuildLzId() {
		return storiedBuildLzId;
	}
	public void setStoriedBuildLzId(Integer storiedBuildLzId) {
		this.storiedBuildLzId = storiedBuildLzId;
	}
	
	@Id
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
	

	@Column(name = "unit_lz_id")
	public Integer getUnitLzId() {
		return unitLzId;
	}
	public void setUnitLzId(Integer unitLzId) {
		this.unitLzId = unitLzId;
	}

	@Column(name = "is_sycn")
	public String getIsSycn() {
		return isSycn;
	}
	public void setIsSycn(String isSycn) {
		this.isSycn = isSycn;
	}
}
