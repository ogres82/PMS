package com.jdry.pms.patrolPlan.pojo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_patrol_node_rec")
public class PatrolNodeRecEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String patrolRecId;
	private int patrolNodeId;
	//虚拟字段
	private String patrolNodeName;
	private String patrolUserId;
	private String patrolUserName;		
	private String patrolContent;
	private String xAxis;
	private String yAxis;
	private long insertTime;


	@Id
	@Column(name = "patrol_rec_id")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getPatrolRecId() {
		return patrolRecId;
	}

	public void setPatrolRecId(String patrolRecId) {
		this.patrolRecId = patrolRecId;
	}

	@Column(name = "patrol_node_id")
	public int getPatrolNodeId() {
		return patrolNodeId;
	}

	public void setPatrolNodeId(int patrolNodeId) {
		this.patrolNodeId = patrolNodeId;
	}

	@Column(name = "patrol_user_id")
	public String getPatrolUserId() {
		return patrolUserId;
	}

	public void setPatrolUserId(String patrolUserId) {
		this.patrolUserId = patrolUserId;
	}

	@Column(name = "patrol_content")
	public String getPatrolContent() {
		return patrolContent;
	}

	public void setPatrolContent(String patrolContent) {
		this.patrolContent = patrolContent;
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

	@Column(name = "insert_time")
	public long getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(long insertTime) {
		this.insertTime = insertTime;
	}
	
	@Formula(value="(SELECT a.patrol_node_name FROM t_patrol_node a WHERE a.patrol_node_id = patrol_node_id )")
	public String getPatrolNodeName() {
		return patrolNodeName;
	}

	public void setPatrolNodeName(String patrolNodeName) {
		this.patrolNodeName = patrolNodeName;
	}

	@Formula(value="(SELECT a.cname_ FROM bdf2_user a WHERE a.username_ = patrol_user_id )")
	public String getPatrolUserName() {
		return patrolUserName;
	}

	public void setPatrolUserName(String patrolUserName) {
		this.patrolUserName = patrolUserName;
	}
	
}
