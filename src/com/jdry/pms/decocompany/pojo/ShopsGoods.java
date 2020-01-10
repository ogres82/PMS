package com.jdry.pms.decocompany.pojo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "t_shops_goods")
public class ShopsGoods {
	  
	String  id;
	String  companyId;
	String  name;
	String  detail;
	Double  price;
	  
	@Id
	@Column(name = "id")
	public String getId(){
		return this.id;
	}
	
	public void setId(String id){
		this.id =  id;
	}
	
	@Column(name = "company_id")
	public String getCompanyId(){
		return this.companyId;
	}
	
	public void setCompanyId(String companyId){
		this.companyId =  companyId;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "detail")
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Column(name = "price")
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
}
