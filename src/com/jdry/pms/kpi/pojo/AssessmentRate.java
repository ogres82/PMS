package com.jdry.pms.kpi.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

@Entity
@Table(name = "t_assessment_rate")
public class AssessmentRate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int rateId;
	private String kpiId;
	private String empPostId;
	private int rateDays;
	private int rateValue;
	
	//虚拟字段
	private String empPostName;
	

	@Id
	@Column(name = "rate_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	public int getRateId() {
		return rateId;
	}

	public void setRateId(int rateId) {
		this.rateId = rateId;
	}

	@Column(name = "kpi_id")
	public String getKpiId() {
		return kpiId;
	}

	public void setKpiId(String kpiId) {
		this.kpiId = kpiId;
	}

	@Column(name = "emp_post_id")
	public String getEmpPostId() {
		return empPostId;
	}

	public void setEmpPostId(String empPostId) {
		this.empPostId = empPostId;
	}

	@Column(name = "rate_days")
	public int getRateDays() {
		return rateDays;
	}

	public void setRateDays(int rateDays) {
		this.rateDays = rateDays;
	}

	@Column(name = "rate_value")
	public int getRateValue() {
		return rateValue;
	}

	public void setRateValue(int rateValue) {
		this.rateValue = rateValue;
	}

	@Formula("(SELECT t.NAME_ FROM bdf2_position t where t.ID_ = emp_post_id)")
	public String getEmpPostName() {
		return empPostName;
	}

	public void setEmpPostName(String empPostName) {
		this.empPostName = empPostName;
	}
}
