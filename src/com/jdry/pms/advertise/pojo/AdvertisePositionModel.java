package com.jdry.pms.advertise.pojo;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "t_advertise_position")
public class AdvertisePositionModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8192483105213114931L;
	private String adv_pos_id;//
	private String adv_pos_code;//
	private String adv_position;//
	private String adv_pos_size;//
	private String adv_pos_status;//
	private String adv_pos_type;//
	private String adv_memo;
	private String create_by;
	private Date create_time;
	
	public String getCreate_by() {
		return create_by;
	}
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getAdv_memo() {
		return adv_memo;
	}
	public void setAdv_memo(String adv_memo) {
		this.adv_memo = adv_memo;
	}
	@Id
	public String getAdv_pos_id() {
		return adv_pos_id;
	}
	public void setAdv_pos_id(String adv_pos_id) {
		this.adv_pos_id = adv_pos_id;
	}
	public String getAdv_pos_code() {
		return adv_pos_code;
	}
	public void setAdv_pos_code(String adv_pos_code) {
		this.adv_pos_code = adv_pos_code;
	}
	public String getAdv_position() {
		return adv_position;
	}
	public void setAdv_position(String adv_position) {
		this.adv_position = adv_position;
	}
	public String getAdv_pos_size() {
		return adv_pos_size;
	}
	public void setAdv_pos_size(String adv_pos_size) {
		this.adv_pos_size = adv_pos_size;
	}
	public String getAdv_pos_status() {
		return adv_pos_status;
	}
	public void setAdv_pos_status(String adv_pos_status) {
		this.adv_pos_status = adv_pos_status;
	}
	public String getAdv_pos_type() {
		return adv_pos_type;
	}
	public void setAdv_pos_type(String adv_pos_type) {
		this.adv_pos_type = adv_pos_type;
	}

}
