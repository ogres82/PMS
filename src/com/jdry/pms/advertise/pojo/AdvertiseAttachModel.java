package com.jdry.pms.advertise.pojo;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "t_advertise_attach")
public class  AdvertiseAttachModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6806062127187024147L;
	private String adv_pos_id;//	
	private String adv_pos_code;//	
	private String contract_code;//	
	private String price;//	
	private String dur_time;//	
	private String adv_business;//	
	private String adv_business_phone;//	
	private String adv_memo;//	
	private String create_by;//	
	private Date create_time;//	
	
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

}
