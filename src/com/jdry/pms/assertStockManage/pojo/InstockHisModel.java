package com.jdry.pms.assertStockManage.pojo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_instock_his")
public class InstockHisModel implements Serializable{
	private static final long serialVersionUID = -821490372692677489L;

	private String instock_id;

	private String suppliy_code;

	private String voucher_id;//凭证编号

	private String suppliy_name;

	private double unit_price;

	private String instock_time;

	private int suppliy_num;
	
	private String item_code;
	
	private String item_name;
	
	private double sum_price;
	
	private String item_type;
	
	private String oper_id;
	
	private String item_id;//物品ID
	
	//private Set<VoucherModel> vouchers = new HashSet<VoucherModel>();

	  @Id
	  @Column(name = "instock_id")
	  @GeneratedValue(generator = "system-uuid")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getInstock_id() {
		return instock_id;
	}

	public void setInstock_id(String instock_id) {
		this.instock_id = instock_id;
	}

	@Column(name = "suppliy_code")
	public String getSuppliy_code() {
		return suppliy_code;
	}

	public void setSuppliy_code(String suppliy_code) {
		this.suppliy_code = suppliy_code;
	}

	@Column(name = "voucher_id")
	public String getVoucher_id() {
		return voucher_id;
	}

	public void setVoucher_id(String voucher_id) {
		this.voucher_id = voucher_id;
	}

	@Column(name = "suppliy_name")
	public String getSuppliy_name() {
		return suppliy_name;
	}

	public void setSuppliy_name(String suppliy_name) {
		this.suppliy_name = suppliy_name;
	}

	@Column(name = "unit_price")
	public double getUnit_price() {
		return unit_price;
	}

	public void setUnit_price(double unit_price) {
		this.unit_price = unit_price;
	}
	
	@Column(name = "instock_time")
	public String getInstock_time() {
		return instock_time;
	}

	public void setInstock_time(String instock_time) {
		this.instock_time = instock_time;
	}

	@Column(name = "suppliy_num")
	public int getSuppliy_num() {
		return suppliy_num;
	}

	public void setSuppliy_num(int suppliy_num) {
		this.suppliy_num = suppliy_num;
	}

	@Column(name = "item_code")
	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	
	@Column(name = "item_name")
	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	@Column(name = "sum_price")
	public double getSum_price() {
		return sum_price;
	}

	public void setSum_price(double sum_price) {
		this.sum_price = sum_price;
	}
	@Column(name = "item_type")
	public String getItem_type() {
		return item_type;
	}

	public void setItem_type(String item_type) {
		this.item_type = item_type;
	}
	@Column(name = "oper_id")
	public String getOper_id() {
		return oper_id;
	}

	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}

	@Column(name = "item_id")
	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	
}
