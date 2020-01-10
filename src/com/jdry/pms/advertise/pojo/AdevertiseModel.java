package com.jdry.pms.advertise.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "v_advertise")
public class AdevertiseModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3160499384474516428L;
	private String a_id;//	
	private String a_code;//	
	private String contract_code;//	
	private String price;//	
	private String dur_time;//	
	private String adv_business;//	
	private String adv_business_phone;//	
	private String adv_memo;//	
	private String create_by;//	
	private Date create_time;//	
	
	private String p_id;//
	private String p_code;//
	private String adv_position;//
	private String adv_pos_size;//
	private String adv_pos_status;//
	private String adv_pos_type;//
	
	private String adv_pos_status_name;//
	private String adv_pos_type_name;//
	
	public String getAdv_pos_status_name() {
		return adv_pos_status_name;
	}
	public void setAdv_pos_status_name(String adv_pos_status_name) {
		this.adv_pos_status_name = adv_pos_status_name;
	}
	public String getAdv_pos_type_name() {
		return adv_pos_type_name;
	}
	public void setAdv_pos_type_name(String adv_pos_type_name) {
		this.adv_pos_type_name = adv_pos_type_name;
	}
	@Id
	public String getA_id() {
		return a_id;
	}
	public void setA_id(String a_id) {
		this.a_id = a_id;
	}
	public String getA_code() {
		return a_code;
	}
	public void setA_code(String a_code) {
		this.a_code = a_code;
	}
	public String getContract_code() {
		return contract_code;
	}
	public void setContract_code(String contract_code) {
		this.contract_code = contract_code;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDur_time() {
		return dur_time;
	}
	public void setDur_time(String dur_time) {
		this.dur_time = dur_time;
	}
	public String getAdv_business() {
		return adv_business;
	}
	public void setAdv_business(String adv_business) {
		this.adv_business = adv_business;
	}
	public String getAdv_business_phone() {
		return adv_business_phone;
	}
	public void setAdv_business_phone(String adv_business_phone) {
		this.adv_business_phone = adv_business_phone;
	}
	public String getAdv_memo() {
		return adv_memo;
	}
	public void setAdv_memo(String adv_memo) {
		this.adv_memo = adv_memo;
	}
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
	public String getP_id() {
		return p_id;
	}
	public void setP_id(String p_id) {
		this.p_id = p_id;
	}
	public String getP_code() {
		return p_code;
	}
	public void setP_code(String p_code) {
		this.p_code = p_code;
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
