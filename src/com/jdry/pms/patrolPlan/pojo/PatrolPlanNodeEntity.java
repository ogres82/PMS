package com.jdry.pms.patrolPlan.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

@Entity
@Table(name = "t_patrol_plan_node")
public class PatrolPlanNodeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private long patrolPlanId;
	private String patrolPlanName;
	private long patrolNodeId;
	private String patrolNodeName;
	Date gmtCreate;
	Date gmtModified;
	@Id
	@Column(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "patrol_plan_id")
	public long getPatrolPlanId() {
		return patrolPlanId;
	}

	public void setPatrolPlanId(long patrolPlanId) {
		this.patrolPlanId = patrolPlanId;
	}

	@Formula(value = "(select a.plan_name from t_patrol_plan a where a.patrol_plan_id = patrol_plan_id)")
	public String getPatrolPlanName() {
		return patrolPlanName;
	}

	public void setPatrolPlanName(String patrolPlanName) {
		this.patrolPlanName = patrolPlanName;
	}

	@Column(name = "patrol_node_id")
	public long getPatrolNodeId() {
		return patrolNodeId;
	}

	public void setPatrolNodeId(long patrolNodeId) {
		this.patrolNodeId = patrolNodeId;
	}

	@Formula(value = "(select a.patrol_node_name from t_patrol_node a where a.patrol_node_id = patrol_node_id)")
	public String getPatrolNodeName() {
		return patrolNodeName;
	}

	public void setPatrolNodeName(String patrolNodeName) {
		this.patrolNodeName = patrolNodeName;
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

}
