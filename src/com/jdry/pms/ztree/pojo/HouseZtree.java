package com.jdry.pms.ztree.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "v_house_ztree")
public class HouseZtree {
	
	@Column(name = "p_id")
	private String pId;
	@Id
	@Column(name = "node_id")
	private String id;
	@Column(name = "node_name")
	private String name;
	@Column(name = "node_level")
	private String nodeLevel;
	@Column(name = "is_open")
	private Boolean  open;
	@Column(name = "lz_id")
	private Integer lzId;

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNodeLevel() {
		return nodeLevel;
	}

	public void setNodeLevel(String nodeLevel) {
		this.nodeLevel = nodeLevel;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public Integer getLzId() {
		return lzId;
	}

	public void setLzId(Integer lzId) {
		this.lzId = lzId;
	}

}
