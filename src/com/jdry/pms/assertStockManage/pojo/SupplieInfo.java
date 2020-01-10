package com.jdry.pms.assertStockManage.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 描述：供货商基本信息
 * @author hezuping
 * @date 2016-01-07 13:44 
 *
 */
@Entity
@Table(name = "t_supplieInfo")
public class SupplieInfo implements Serializable 
{

	
	private static final long serialVersionUID = 1L;

	private String suppliy_id;
	
	private String suppliy_code;
	
	private String suppliy_name;
	
	private String link_name;
	
	private String link_phone;
	
    private String link_address;
    
    private String fax;
    
    private String zip_code;
    
    private String url;
    
    private String bank;
    
    private String bank_account;
    
    private String linke_moble;
    
    private String qq;
	
	
	private String other;

	  @Id
	  @Column(name = "suppliy_id")
	  @GeneratedValue(generator = "system-uuid")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")   
	public String getSuppliy_id() {
		return suppliy_id;
	}

	public void setSuppliy_id(String suppliy_id) {
		this.suppliy_id = suppliy_id;
	}

	@Column(name = "suppliy_code")
	public String getSuppliy_code() {
		return suppliy_code;
	}

	public void setSuppliy_code(String suppliy_code) {
		this.suppliy_code = suppliy_code;
	}

	@Column(name = "suppliy_name")
	public String getSuppliy_name() {
		return suppliy_name;
	}

	public void setSuppliy_name(String suppliy_name) {
		this.suppliy_name = suppliy_name;
	}

	@Column(name = "link_name")
	public String getLink_name() {
		return link_name;
	}

	public void setLink_name(String link_name) {
		this.link_name = link_name;
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

	@Column(name = "link_address")
	public String getLink_address() {
		return link_address;
	}

	public void setLink_address(String link_address) {
		this.link_address = link_address;
	}
	@Column(name = "fax")
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "zip_code")
	public String getZip_code() {
		return zip_code;
	}

	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}

	@Column(name = "url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "bank")
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	@Column(name = "bank_account")
	public String getBank_account() {
		return bank_account;
	}

	public void setBank_account(String bank_account) {
		this.bank_account = bank_account;
	}

	@Column(name = "linke_moble")
	public String getLinke_moble() {
		return linke_moble;
	}

	public void setLinke_moble(String linke_moble) {
		this.linke_moble = linke_moble;
	}

	@Column(name = "qq")
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}
	
	

	

	
	
	
}
