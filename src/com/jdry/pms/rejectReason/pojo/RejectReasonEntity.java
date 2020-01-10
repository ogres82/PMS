package com.jdry.pms.rejectReason.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_reject_reason")
public class RejectReasonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int rejectReasonId;
	private String rejectType;
	private String rejectReason;
	private int rejectNum;

	@Id
	@Column(name = "reject_reason_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getRejectReasonId() {
		return rejectReasonId;
	}

	public void setRejectReasonId(int rejectReasonId) {
		this.rejectReasonId = rejectReasonId;
	}

	@Column(name = "reject_type")
	public String getRejectType() {
		return rejectType;
	}

	public void setRejectType(String rejectType) {
		this.rejectType = rejectType;
	}

	@Column(name = "reject_reason")
	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	@Column(name = "reject_num")
	public int getRejectNum() {
		return rejectNum;
	}

	public void setRejectNum(int rejectNum) {
		this.rejectNum = rejectNum;
	}

}
