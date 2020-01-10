package com.jdry.pms.assertStockManage.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * 物品档案
 * @author hezuping
 *
 */
@Entity
@Table(name = "t_itmsfiles")
public class Titmsfiles implements Serializable
{
	private String item_id;

	private String item_code;

	private String bar_code;//物品条码

	private String item_name;

	private String item_type;

	private String item_unit;

	private Double stock_uplimit;

	private Double stock_lowerlimit;

	private Double defu_inprice;

	private Double defu_outprice;

	private Double stock_avgprice;
	
	private String item_flag;
	
	private String item_Ptype;
	

	  @Id
	  @Column(name = "item_id")
	  @GeneratedValue(generator = "system-uuid")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")   
	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	@Column(name = "item_code")
	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	@Column(name = "bar_code")
	public String getBar_code() {
		return bar_code;
	}

	public void setBar_code(String bar_code) {
		this.bar_code = bar_code;
	}

	@Column(name = "item_name")
	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	@Column(name = "item_type")
	public String getItem_type() {
		return item_type;
	}

	public void setItem_type(String item_type) {
		this.item_type = item_type;
	}

	@Column(name = "item_unit")
	public String getItem_unit() {
		return item_unit;
	}

	public void setItem_unit(String item_unit) {
		this.item_unit = item_unit;
	}

	@Column(name = "stock_uplimit", columnDefinition="double(10,2) default '0.00'")
	public Double getStock_uplimit() {
		return stock_uplimit;
	}

	public void setStock_uplimit(Double stock_uplimit) {
		this.stock_uplimit = stock_uplimit;
	}

	@Column(name = "stock_lowerlimit", columnDefinition="double(10,2) default '0.00'")
	public Double getStock_lowerlimit() {
		return stock_lowerlimit;
	}

	public void setStock_lowerlimit(Double stock_lowerlimit) {
		this.stock_lowerlimit = stock_lowerlimit;
	}

	@Column(name = "defu_inprice", columnDefinition="double(10,2) default '0.00'")
	public Double getDefu_inprice() {
		return defu_inprice;
	}

	public void setDefu_inprice(Double defu_inprice) {
		this.defu_inprice = defu_inprice;
	}

	@Column(name = "defu_outprice", columnDefinition="double(10,2) default '0.00'")
	public Double getDefu_outprice() {
		return defu_outprice;
	}

	public void setDefu_outprice(Double defu_outprice) {
		this.defu_outprice = defu_outprice;
	}

	@Column(name = "stock_avgprice", columnDefinition="double(10,2) default '0.00'")
	public Double getStock_avgprice() {
		return stock_avgprice;
	}

	public void setStock_avgprice(Double stock_avgprice) {
		this.stock_avgprice = stock_avgprice;
	}

	@Column(name = "item_flag")
	public String getItem_flag() {
		return item_flag;
	}

	public void setItem_flag(String item_flag) {
		this.item_flag = item_flag;
	}

	@Column(name = "item_Ptype")
	public String getItem_Ptype() {
		return item_Ptype;
	}

	public void setItem_Ptype(String item_Ptype) {
		this.item_Ptype = item_Ptype;
	}

}
