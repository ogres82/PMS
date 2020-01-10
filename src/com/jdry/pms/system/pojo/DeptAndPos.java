package com.jdry.pms.system.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "v_dept_pos_info")
public class DeptAndPos {

	private String Id;
	private String deptId;
	private String deptName;
	private String posId;
	private String posName;
	
	
	@Id
	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	
	@Column(name = "dept_id")
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "dept_name")
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	@Column(name = "pos_id")
	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	@Column(name = "pos_name")
	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

}