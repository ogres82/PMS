package com.jdry.pms.houseWork.pojo;

import java.io.Serializable;
/**
 * 描述：项目收费及其项目信息实体
 * @author hezuping
 *
 */
public class HouseworkChargeEntity implements Serializable
{
	private String id;//收费项目ID
	private String event_id;
	private String send_no;
	private Double money_amount;
	private String project_id;//项目编号

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEvent_id() {
		return event_id;
	}
	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}
	public String getSend_no() {
		return send_no;
	}
	public void setSend_no(String send_no) {
		this.send_no = send_no;
	}
	public Double getMoney_amount() {
		return money_amount;
	}
	public void setMoney_amount(Double money_amount) {
		this.money_amount = money_amount;
	}
	public String getProject_id() {
		return project_id;
	}
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}

}
