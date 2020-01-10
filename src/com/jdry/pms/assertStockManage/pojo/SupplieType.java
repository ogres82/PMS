package com.jdry.pms.assertStockManage.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 物资类别表
 * @author hezuping
 * 时间：2016-01-05
 */
@Entity
@Table(name = "t_supplytype")
public class SupplieType implements Serializable
{
 
	private String supplytype_id;
	
	private String supply_code;
	private String type_name;
	
	private String type_orther;
	//父类类型ID来自另外一张表
	private String parent_supp_id;

	  @Id
	  @Column(name = "supplytype_id")
	  @GeneratedValue(generator = "system-uuid")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")   
	public String getSupplytype_id() 
	{
		return supplytype_id;
	}

	public void setSupplytype_id(String supplytype_id) 
	{
		this.supplytype_id = supplytype_id;
	}

	@Column(name = "type_name")
	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	@Column(name = "type_orther")
	public String getType_orther() {
		return type_orther;
	}

	public void setType_orther(String type_orther)
	{
		this.type_orther = type_orther;
	}

	@Column(name = "supply_code")
	public String getSupply_code() {
		return supply_code;
	}

	public void setSupply_code(String supply_code) {
		this.supply_code = supply_code;
	}

	@Column(name = "parent_supp_id")
	public String getParent_supp_id() {
		return parent_supp_id;
	}

	public void setParent_supp_id(String parent_supp_id) {
		this.parent_supp_id = parent_supp_id;
	}
	
	
}
