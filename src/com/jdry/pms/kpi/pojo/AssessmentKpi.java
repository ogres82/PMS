package com.jdry.pms.kpi.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

@Entity
@Table(name = "t_assessment_kpi")
public class AssessmentKpi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String kpiId;
	private String kpiParentId;
	private String kpiName;
	private int kpiLv;
	private int kpiValue;
	private String kpiDetail;
	private String kpiMethod;
	private String empPostId;
	private String operId;
	private Date insertDate;
	private Date updateDate;
	private String kpiType;

	private List<AssessmentRate> kpiRate;

	// 虚拟字段
	private String empPostName;
	private String deptName;	
	private String operName;

	@Id
	@Column(name = "kpi_id")
	public String getKpiId() {
		return kpiId;
	}

	public void setKpiId(String kpiId) {
		this.kpiId = kpiId;
	}

	@Column(name = "kpi_parent_id")
	public String getKpiParentId() {
		return kpiParentId;
	}

	public void setKpiParentId(String kpiParentId) {
		this.kpiParentId = kpiParentId;
	}

	@Column(name = "kpi_name")
	public String getKpiName() {
		return kpiName;
	}

	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}

	@Column(name = "kpi_lv")
	public int getKpiLv() {
		return kpiLv;
	}

	public void setKpiLv(int kpiLv) {
		this.kpiLv = kpiLv;
	}

	@Column(name = "kpi_value")
	public int getKpiValue() {
		return kpiValue;
	}

	public void setKpiValue(int kpiValue) {
		this.kpiValue = kpiValue;
	}

	@Column(name = "kpi_detail")
	public String getKpiDetail() {
		return kpiDetail;
	}

	public void setKpiDetail(String kpiDetail) {
		this.kpiDetail = kpiDetail;
	}

	@Column(name = "kpi_method")
	public String getKpiMethod() {
		return kpiMethod;
	}

	public void setKpiMethod(String kpiMethod) {
		this.kpiMethod = kpiMethod;
	}

	@Column(name = "emp_post_id")
	public String getEmpPostId() {
		return empPostId;
	}

	public void setEmpPostId(String empPostId) {
		this.empPostId = empPostId;
	}

	@Column(name = "oper_id")
	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}

	@Column(name = "insert_date")
	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	@Column(name = "update_date")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "kpi_id")
	public List<AssessmentRate> getKpiRate() {
		return kpiRate;
	}

	public void setKpiRate(List<AssessmentRate> kpiRate) {
		this.kpiRate = kpiRate;
	}

	@Formula("(SELECT group_concat(t.NAME_) FROM bdf2_position t WHERE FIND_IN_SET(t.ID_,emp_post_id))")
	public String getEmpPostName() {
		return empPostName;
	}

	public void setEmpPostName(String empPostName) {
		this.empPostName = empPostName;
	}

	@Formula("(SELECT t.CNAME_ FROM bdf2_user t WHERE t.USERNAME_= oper_id)")
	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	@Column(name = "kpi_type")
	public String getKpiType() {
		return kpiType;
	}

	public void setKpiType(String kpiType) {
		this.kpiType = kpiType;
	}

	@Formula("(SELECT group_concat(t.NAME_) FROM bdf2_dept t WHERE FIND_IN_SET(t.ID_,emp_post_id))")
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

}
