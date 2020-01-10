package com.jdry.pms.comm.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_B_BUSINESS_KEY")
public class BusinessKeyEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5768321919309656838L;

	private String buseness_id;//主键
	private String business_key;//类型
	private String business_type;//年-月-日，年-月，年（默认成年月日）
	private Date business_date;//时间
	private int business_num;//当前序号
	
	@Id
	public String getBuseness_id() {
		return buseness_id;
	}
	public void setBuseness_id(String buseness_id) {
		this.buseness_id = buseness_id;
	}
	public String getBusiness_key() {
		return business_key;
	}
	public void setBusiness_key(String business_key) {
		this.business_key = business_key;
	}
	public String getBusiness_type() {
		return business_type;
	}
	public void setBusiness_type(String business_type) {
		this.business_type = business_type;
	}
	public Date getBusiness_date() {
		return business_date;
	}
	public void setBusiness_date(Date business_date) {
		this.business_date = business_date;
	}
	public int getBusiness_num() {
		return business_num;
	}
	public void setBusiness_num(int business_num) {
		this.business_num = business_num;
	}
	
	
}
