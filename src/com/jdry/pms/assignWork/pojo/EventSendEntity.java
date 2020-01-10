package com.jdry.pms.assignWork.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 派工历史纪录表
 * @author hezuping
 *
 */
@Entity
@Table(name = "t_r_eventsend")
public class EventSendEntity implements Serializable
{
	
	private String id;
	
	private String event_id;//事件单号
	
	private Date handle_time;
	
	private String ownHandler;
	
	private String handler_dept;
	
	private String handler_phone;
	
	private String status;
	
	private String del_status;
	
	private String mtn_type;

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

	@Column(name = "event_id")
	public String getEvent_id() {
		return event_id;
	}

	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}

	@Column(name = "handle_time")
	public Date getHandle_time() {
		return handle_time;
	}

	public void setHandle_time(Date handle_time) {
		this.handle_time = handle_time;
	}

	@Column(name = "handlers")
	public String getOwnHandler() {
		return ownHandler;
	}

	public void setOwnHandler(String ownHandler) {
		this.ownHandler = ownHandler;
	}

	@Column(name = "handler_dept")
	public String getHandler_dept() {
		return handler_dept;
	}

	public void setHandler_dept(String handler_dept) {
		this.handler_dept = handler_dept;
	}

	public String getHandler_phone() {
		return handler_phone;
	}

	public void setHandler_phone(String handler_phone) {
		this.handler_phone = handler_phone;
	}

	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "del_status")
	public String getDel_status() {
		return del_status;
	}

	public void setDel_status(String del_status) {
		this.del_status = del_status;
	}

	public String getMtn_type() {
		return mtn_type;
	}

	@Column(name = "mtn_type")
	public void setMtn_type(String mtn_type) {
		this.mtn_type = mtn_type;
	}

}
