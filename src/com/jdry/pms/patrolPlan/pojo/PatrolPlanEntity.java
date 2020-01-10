package com.jdry.pms.patrolPlan.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;


@Entity
@Table(name = "t_patrol_plan")
public class PatrolPlanEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private long patrolPlanId;
	private String planName;
	private String planType;
	private String planTimes;
	private Date startDate;
	private Date endDate;
	private Date gmtCreate;
	private Date gmtModified;
	private String operName;
	private String operId;
	private String remark;
	//虚拟字段 计划中的巡逻点数量
	private int patrolNodeNum;
	
	@Id
	@Column(name = "patrol_plan_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getPatrolPlanId() {
		return patrolPlanId;
	}

	public void setPatrolPlanId(long patrolPlanId) {
		this.patrolPlanId = patrolPlanId;
	}

	@Column(name = "plan_name")
	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	@Column(name = "plan_type")
	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	@Column(name = "plan_times")
	public String getPlanTimes() {
		return planTimes;
	}

	public void setPlanTimes(String planTimes) {
		this.planTimes = planTimes;
	}

	@Column(name = "start_date")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "end_date")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "gmt_create")
	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	@Column(name = "gmt_modified")
	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	@Formula(value = "(SELECT a.cname_ FROM bdf2_user a WHERE a.username_ = oper_id )")
	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	@Column(name = "oper_id")
	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Formula(value = "(SELECT count(1) FROM t_patrol_plan_node a WHERE a.patrol_plan_id = patrol_plan_id )")
	public int getPatrolNodeNum() {
		return patrolNodeNum;
	}

	public void setPatrolNodeNum(int patrolNodeNum) {
		this.patrolNodeNum = patrolNodeNum;
	}
}
