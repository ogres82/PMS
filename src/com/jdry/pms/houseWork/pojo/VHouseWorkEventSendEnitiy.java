package com.jdry.pms.houseWork.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table(name = "v_t_housework_eventsend")
public class VHouseWorkEventSendEnitiy  implements Serializable
{
	private String event_no;
	private String event_title;
	private String call_phone;
	private Date accept_time;
	private String event_from;
	private String event_source_name;
	private Date pre_time;
	private String record_id;
	private String bpm_processId;
	private String rpt_name;
	private String event_content;
	private String verify_oper_id;
	private String user_address;
	
	private String id;
	private String oper_id;
	private Date send_time;
	private String send_state;
	private String event_state;
	private String send_no;
	private String oper_name;
	private String send_id;
	private String handle_content;
	private Date arrv_time;
	private String houseKeepingPay;
	public String getEvent_no() {
		return event_no;
	}
	public String getEvent_title() {
		return event_title;
	}
	public String getCall_phone() {
		return call_phone;
	}
	public Date getAccept_time() {
		return accept_time;
	}
	public String getEvent_from() {
		return event_from;
	}
	public String getEvent_source_name() {
		return event_source_name;
	}
	public Date getPre_time() {
		return pre_time;
	}
	public String getRecord_id() {
		return record_id;
	}
	public String getRpt_name() {
		return rpt_name;
	}
	public String getEvent_content() {
		return event_content;
	}
	public String getVerify_oper_id() {
		return verify_oper_id;
	}
	public String getUser_address() {
		return user_address;
	}
	@Id
	public String getId() {
		return id;
	}
	public String getOper_id() {
		return oper_id;
	}
	public Date getSend_time() {
		return send_time;
	}
	public String getSend_state() {
		return send_state;
	}
	public String getEvent_state() {
		return event_state;
	}
	public String getSend_no() {
		return send_no;
	}
	public String getOper_name() {
		return oper_name;
	}
	public String getSend_id() {
		return send_id;
	}
	public String getHandle_content() {
		return handle_content;
	}
	public Date getArrv_time() {
		return arrv_time;
	}
	public String getHouseKeepingPay() {
		return houseKeepingPay;
	}
	public void setEvent_no(String event_no) {
		this.event_no = event_no;
	}
	public void setEvent_title(String event_title) {
		this.event_title = event_title;
	}
	public void setCall_phone(String call_phone) {
		this.call_phone = call_phone;
	}
	public void setAccept_time(Date accept_time) {
		this.accept_time = accept_time;
	}
	public void setEvent_from(String event_from) {
		this.event_from = event_from;
	}
	public void setEvent_source_name(String event_source_name) {
		this.event_source_name = event_source_name;
	}
	public void setPre_time(Date pre_time) {
		this.pre_time = pre_time;
	}
	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}
	public void setRpt_name(String rpt_name) {
		this.rpt_name = rpt_name;
	}
	public void setEvent_content(String event_content) {
		this.event_content = event_content;
	}
	public void setVerify_oper_id(String verify_oper_id) {
		this.verify_oper_id = verify_oper_id;
	}
	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}
	public void setSend_time(Date send_time) {
		this.send_time = send_time;
	}
	public void setSend_state(String send_state) {
		this.send_state = send_state;
	}
	public void setEvent_state(String event_state) {
		this.event_state = event_state;
	}
	public void setSend_no(String send_no) {
		this.send_no = send_no;
	}
	public void setOper_name(String oper_name) {
		this.oper_name = oper_name;
	}
	public void setSend_id(String send_id) {
		this.send_id = send_id;
	}
	public void setHandle_content(String handle_content) {
		this.handle_content = handle_content;
	}
	public void setArrv_time(Date arrv_time) {
		this.arrv_time = arrv_time;
	}
	public void setHouseKeepingPay(String houseKeepingPay) {
		this.houseKeepingPay = houseKeepingPay;
	}
	public String getBpm_processId() {
		return bpm_processId;
	}
	public void setBpm_processId(String bpm_processId) {
		this.bpm_processId = bpm_processId;
	}
	

}
