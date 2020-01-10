package com.jdry.pms.chargeManager.pojo;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="t_charge_type_room_rela_log")
public class ChargeTypeRoomRelaLog {
	@Id
	@Column(name = "change_id")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String changeId;
	
	@Column(name = "room_id")
	private String roomId;
	
	@Column(name = "owenr_id")
	private String owenrId;
	
	@Column(name = "change_count")
	private BigDecimal changeCount;
	
	@Column(name = "change_type")
	private String changeType;
	
	@Column(name = "remarks")
	private String remarks;
	
	@Column(name = "insert_time")
	private Date insertTime  = new Date();
	
	@Column(name = "oper_id")
	private String operId;

	@Column(name = "charge_id")
	private String chargeID;
	

	public String getChangeId() {
		return changeId;
	}

	public void setChangeId(String changeId) {
		this.changeId = changeId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getOwenrId() {
		return owenrId;
	}

	public void setOwenrId(String owenrId) {
		this.owenrId = owenrId;
	}

	public BigDecimal getChangeCount() {
		return changeCount;
	}

	public void setChangeCount(BigDecimal changeCount) {
		this.changeCount = changeCount;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}

	public String getChargeID() {
		return chargeID;
	}

	public void setChargeID(String chargeID) {
		this.chargeID = chargeID;
	}
}
