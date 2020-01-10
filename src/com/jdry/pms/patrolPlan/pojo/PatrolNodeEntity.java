package com.jdry.pms.patrolPlan.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;


@Entity
@Table(name = "t_patrol_node")
public class PatrolNodeEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int patrolNodeId;
	private String patrolNodeName;
	private String patrolState;
	private String operId;
	private String operName;//虚拟字段
	private long createTime;
	private String xAxis;
	private String yAxis;
	

	@Id
	@Column(name = "patrol_node_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getPatrolNodeId() {
		return patrolNodeId;
	}

	public void setPatrolNodeId(int patrolNodeId) {
		this.patrolNodeId = patrolNodeId;
	}

	@Column(name = "patrol_node_name")
	public String getPatrolNodeName() {
		return patrolNodeName;
	}

	public void setPatrolNodeName(String patrolNodeName) {
		this.patrolNodeName = patrolNodeName;
	}

	@Column(name = "patrol_state")
	public String getPatrolState() {
		return patrolState;
	}

	public void setPatrolState(String patrolState) {
		this.patrolState = patrolState;
	}

	@Column(name = "oper_id")
	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}

	@Column(name = "create_time")
	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	@Column(name = "x_axis")
	public String getxAxis() {
		return xAxis;
	}

	public void setxAxis(String xAxis) {
		this.xAxis = xAxis;
	}

	@Column(name = "y_axis")
	public String getyAxis() {
		return yAxis;
	}

	public void setyAxis(String yAxis) {
		this.yAxis = yAxis;
	}
	
	@Formula(value="(SELECT a.cname_ FROM bdf2_user a WHERE a.username_ = oper_id )")
	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}
	
	
}
