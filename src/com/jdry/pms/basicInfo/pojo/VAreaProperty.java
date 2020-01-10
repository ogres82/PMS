package com.jdry.pms.basicInfo.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 小区信息表
 * 
 * @author Buxiaochao
 *
 */
@Entity
@Table(name = "v_t_area_property")
public class VAreaProperty {

	private String communityId;
	private String communityName;
	private String belongBuildId;
	private String buildName;
	private String remark;
	private Integer lzId;
	private Integer lzBuildId;

	@Id
	@Column(name = "community_Id")
	public String getCommunityId() {
		return this.communityId;	}

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

	@Column(name = "belong_build_id")
	public String getBelongBuildId() {
		return this.belongBuildId;
	}

	public void setBelongBuildId(String belongBuildId) {
		this.belongBuildId = belongBuildId;
	}

	@Column(name = "build_name")
	public String getBuildName() {
		return buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
}
