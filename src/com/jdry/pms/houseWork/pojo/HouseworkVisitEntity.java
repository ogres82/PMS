package com.jdry.pms.houseWork.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 回访实体
 * 
 * @author hezuping
 * 
 */
@Entity
@Table(name = "t_housework_visit")
public class HouseworkVisitEntity implements Serializable {
	private String id;
	private String visit_no;
	private String event_id;
	
	private String visit_evaluate;
	private String visit_content;
	
	private Date visit_time;
	private String visit_oper_id;
	private String visit_record;
	private String visit_from;
	
	private String visist_levcontent;//评价内容


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

	@Column(name = "visit_no")
	public String getVisit_no() {
		return visit_no;
	}

	public void setVisit_no(String visit_no) {
		this.visit_no = visit_no;
	}

	@Column(name = "visit_evaluate")
	public String getVisit_evaluate() {
		return visit_evaluate;
	}

	public void setVisit_evaluate(String visit_evaluate) {
		this.visit_evaluate = visit_evaluate;
	}

	@Column(name = "visit_content")
	public String getVisit_content() {
		return visit_content;
	}

	public void setVisit_content(String visit_content) {
		this.visit_content = visit_content;
	}

	@Column(name = "visit_time")
	public Date getVisit_time() {
		return visit_time;
	}

	public void setVisit_time(Date visit_time) {
		this.visit_time = visit_time;
	}

	@Column(name = "visit_oper_id")
	public String getVisit_oper_id() {
		return visit_oper_id;
	}

	public void setVisit_oper_id(String visit_oper_id) {
		this.visit_oper_id = visit_oper_id;
	}

	@Column(name = "visit_record")
	public String getVisit_record() {
		return visit_record;
	}

	public void setVisit_record(String visit_record) {
		this.visit_record = visit_record;
	}

	@Column(name = "visit_from")
	public String getVisit_from() {
		return visit_from;
	}

	public void setVisit_from(String visit_from) {
		this.visit_from = visit_from;
	}

	@Column(name = "event_id")
	public String getEvent_id() {
		return event_id;
	}

	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}

	@Column(name = "visist_levcontent")
	public String getVisist_levcontent() {
		return visist_levcontent;
	}

	public void setVisist_levcontent(String visist_levcontent) {
		this.visist_levcontent = visist_levcontent;
	}

}
