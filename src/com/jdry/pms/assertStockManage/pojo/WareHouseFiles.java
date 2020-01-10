package com.jdry.pms.assertStockManage.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 仓库档案信息
 * @author hezuping
 * date:2016-01-10 22:29:30
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "t_warehouse")
public class WareHouseFiles implements Serializable 
{
	private String warehouse_id;

	private String warehouse_code;

	private String warehouse_name;

	private String warehouse_address;

	private String link_man;

	private String link_phone;

	private String other;

	 @Id
	  @Column(name = "warehouse_id")
	  @GeneratedValue(generator = "system-uuid")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")   
	public String getWarehouse_id() {
		return warehouse_id;
	}

	public void setWarehouse_id(String warehouse_id) {
		this.warehouse_id = warehouse_id;
	}

	@Column(name = "warehouse_code")
	public String getWarehouse_code() {
		return warehouse_code;
	}

	public void setWarehouse_code(String warehouse_code) {
		this.warehouse_code = warehouse_code;
	}

	@Column(name = "warehouse_name")
	public String getWarehouse_name() {
		return warehouse_name;
	}

	public void setWarehouse_name(String warehouse_name) {
		this.warehouse_name = warehouse_name;
	}

	@Column(name = "warehouse_address")
	public String getWarehouse_address() {
		return warehouse_address;
	}

	public void setWarehouse_address(String warehouse_address) {
		this.warehouse_address = warehouse_address;
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

	@Column(name = "other")
	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}


}
