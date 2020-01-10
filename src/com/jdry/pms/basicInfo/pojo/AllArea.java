package com.jdry.pms.basicInfo.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 楼盘信息表
 * 
 * @author Buxiaochao
 *
 */
@Entity
@Table(name = "t_all_area")
public class AllArea {

	private String buildId;
	private String buildName;
	private String remark;
	private int lzId;

	@Id
	@Column(name = "build_id")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
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

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "lz_id")
	public int getLzId() {
		return lzId;
	}

	public void setLzId(int lzId) {
		this.lzId = lzId;
	}
}
