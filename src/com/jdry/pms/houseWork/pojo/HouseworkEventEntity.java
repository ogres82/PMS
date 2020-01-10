package com.jdry.pms.houseWork.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 描述：家政事件单实体
 * 
 * @author hezuping
 * 
 */
@Entity
@Table(name = "t_housework_event")
public class HouseworkEventEntity implements Serializable {
	
	private String id;

	private String event_no;

	private String event_title;

	private String event_content;

	private String user_address;

	private String link_man;

	private String link_phone;

	private String record_id;// 录音编号

	private String room_id;

	private String project_id;

	private Date accept_time;

	private String call_phone;

	private String event_from;

	private Date pre_time;// 预约时间

	private Double serv_price;

	private String event_state;// 事件状态

	private Date finish_time;

	//private String opr_id;// 建单人ID

	private String verify_oper_id;// 受理人编号
	
	private String verify_oper_name;

	private String visit_state;// 回访状态

	private String visit_oper_id;// 回访人员编号
	
	private Long bpm_processId;
	
	private String other;
	
	private String link_man_name;//联系人
	

	  @Id
	  @Column(name = "id")
	  @GeneratedValue(generator = "system-uuid")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "event_no")
	public String getEvent_no() {
		return event_no;
	}

	public void setEvent_no(String event_no) {
		this.event_no = event_no;
	}

	@Column(name = "event_title")
	public String getEvent_title() {
		return event_title;
	}

	public void setEvent_title(String event_title) {
		this.event_title = event_title;
	}

	@Column(name = "event_content")
	public String getEvent_content() {
		return event_content;
	}

	public void setEvent_content(String event_content) {
		this.event_content = event_content;
	}

	@Column(name = "user_address")
	public String getUser_address() {
		return user_address;
	}

	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}

	@Column(name = "link_man")
	public String getLink_man() {
		return link_man;
	}

	public void setLink_man(String link_man) {
		this.link_man = link_man;
	}

	@Column(name = "link_phone")
	public String getLink_phone() {
		return link_phone;
	}

	public void setLink_phone(String link_phone) {
		this.link_phone = link_phone;
	}

	@Column(name = "record_id")
	public String getRecord_id() {
		return record_id;
	}

	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}

	@Column(name = "room_id")
	public String getRoom_id() {
		return room_id;
	}

	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}

	@Column(name = "project_id")
	public String getProject_id() {
		return project_id;
	}

	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}

	@Column(name = "accept_time")
	public Date getAccept_time() {
		return accept_time;
	}

	public void setAccept_time(Date accept_time) {
		this.accept_time = accept_time;
	}

	@Column(name = "call_phone")
	public String getCall_phone() {
		return call_phone;
	}

	public void setCall_phone(String call_phone) {
		this.call_phone = call_phone;
	}

	@Column(name = "event_from")
	public String getEvent_from() {
		return event_from;
	}

	public void setEvent_from(String event_from) {
		this.event_from = event_from;
	}

	@Column(name = "pre_time")
	public Date getPre_time() {
		return pre_time;
	}

	public void setPre_time(Date pre_time) {
		this.pre_time = pre_time;
	}

	@Column(name = "serv_price")
	public Double getServ_price() {
		return serv_price;
	}

	public void setServ_price(Double serv_price) {
		this.serv_price = serv_price;
	}

	@Column(name = "event_state")
	public String getEvent_state() {
		return event_state;
	}

	public void setEvent_state(String event_state) {
		this.event_state = event_state;
	}

	@Column(name = "finish_time")
	public Date getFinish_time() {
		return finish_time;
	}

	public void setFinish_time(Date finish_time) {
		this.finish_time = finish_time;
	}

	
	@Column(name = "verify_oper_id")
	public String getVerify_oper_id() {
		return verify_oper_id;
	}

	public void setVerify_oper_id(String verify_oper_id) {
		this.verify_oper_id = verify_oper_id;
	}

	@Column(name = "visit_state")
	public String getVisit_state() {
		return visit_state;
	}

	public void setVisit_state(String visit_state) {
		this.visit_state = visit_state;
	}

	@Column(name = "visit_oper_id")
	public String getVisit_oper_id() {
		return visit_oper_id;
	}

	public void setVisit_oper_id(String visit_oper_id) {
		this.visit_oper_id = visit_oper_id;
	}

	@Column(name = "bpm_processId")
	public Long getBpm_processId() {
		return bpm_processId;
	}

	public void setBpm_processId(Long bpm_processId) {
		this.bpm_processId = bpm_processId;
	}

	@Column(name = "other")
	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	@Column(name = "link_man_name")
	public String getLink_man_name() {
		return link_man_name;
	}

	public void setLink_man_name(String link_man_name) {
		this.link_man_name = link_man_name;
	}

	@Transient
	public String getVerify_oper_name() {
		return verify_oper_name;
	}

	public void setVerify_oper_name(String verify_oper_name) {
		this.verify_oper_name = verify_oper_name;
	}

	

}
