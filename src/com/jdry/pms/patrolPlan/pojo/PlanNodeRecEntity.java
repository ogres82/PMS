package com.jdry.pms.patrolPlan.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "v_plan_node_rec")
public class PlanNodeRecEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String patrolRecId;
	private long patrolPlanId;
	private long patrolNodeId;
	private String planName;
	private String patrolNodeName;
	private String patrolUserName;
	private String patrolUserId;
	private String patrolContent;
	private long insertTime;
	private String queryDate;

	@Id
	@Column(name = "patrol_rec_id")
	public String getPatrolRecId() {
		return patrolRecId;
	}

	public void setPatrolRecId(String patrolRecId) {
		this.patrolRecId = patrolRecId;
	}
	@Id
	@Column(name = "patrol_plan_id")
	public long getPatrolPlanId() {
		return patrolPlanId;
	}

	public void setPatrolPlanId(long patrolPlanId) {
		this.patrolPlanId = patrolPlanId;
	}
	@Id
	@Column(name = "patrol_node_id")
	public long getPatrolNodeId() {
		return patrolNodeId;
	}

	public void setPatrolNodeId(long patrolNodeId) {
		this.patrolNodeId = patrolNodeId;
	}

	@Column(name = "plan_name")
	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	@Column(name = "patrol_node_name")
	public String getPatrolNodeName() {
		return patrolNodeName;
	}

	public void setPatrolNodeName(String patrolNodeName) {
		this.patrolNodeName = patrolNodeName;
	}

	@Column(name = "patrol_user_name")
	public String getPatrolUserName() {
		return patrolUserName;
	}

	public void setPatrolUserName(String patrolUserName) {
		this.patrolUserName = patrolUserName;
	}

	@Column(name = "patrol_user_id")
	public String getPatrolUserId() {
		return patrolUserId;
	}

	public void setPatrolUserId(String patrolUserId) {
		this.patrolUserId = patrolUserId;
	}

	@Column(name = "patrol_Content")
	public String getPatrolContent() {
		return patrolContent;
	}

	public void setPatrolContent(String patrolContent) {
		this.patrolContent = patrolContent;
	}

	@Column(name = "insert_time")
	public long getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(long insertTime) {
		this.insertTime = insertTime;
	}

	@Column(name = "query_date")
	public String getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}

}
