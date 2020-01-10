package com.jdry.pms.assertStockManage.pojo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 描述：单据凭证实体
 * @author hezuping
 * 时间：2016-01-12 15:03
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "t_voucher")
public class VoucherModel implements Serializable
{
	private String voucher_id;

	private String voucher_code;

	private String occurren_date;

	private String owne_stock;

	private String t_handler;

	private String suppliy_code;
	
	//private String suppliy_name;新加

	private String orther;
	
	private String instok_type;
	

	  @Id
	  @Column(name = "voucher_id")
	  @GeneratedValue(generator = "system-uuid")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")   
	public String getVoucher_id() {
		return voucher_id;
	}

	public void setVoucher_id(String voucher_id) {
		this.voucher_id = voucher_id;
	}

	@Column(name = "voucher_code")
	public String getVoucher_code() {
		return voucher_code;
	}

	public void setVoucher_code(String voucher_code) {
		this.voucher_code = voucher_code;
	}

	@Column(name = "occurren_date")
	public String getOccurren_date() {
		return occurren_date;
	}

	public void setOccurren_date(String occurren_date) {
		this.occurren_date = occurren_date;
	}

	@Column(name = "owne_stock")
	public String getOwne_stock() {
		return owne_stock;
	}

	public void setOwne_stock(String owne_stock) {
		this.owne_stock = owne_stock;
	}

	

	@Column(name = "suppliy_code")
	public String getSuppliy_code() {
		return suppliy_code;
	}

	public void setSuppliy_code(String suppliy_code) {
		this.suppliy_code = suppliy_code;
	}

	@Column(name = "orther")
	public String getOrther() {
		return orther;
	}

	public void setOrther(String orther) {
		this.orther = orther;
	}
	@Column(name = "t_handler")
	public String getT_handler() {
		return t_handler;
	}

	public void setT_handler(String t_handler) {
		this.t_handler = t_handler;
	}

	@Column(name = "instok_type")
	public String getInstok_type() {
		return instok_type;
	}

	public void setInstok_type(String instok_type) {
		this.instok_type = instok_type;
	}



}
