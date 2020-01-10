package com.jdry.pms.dir.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DIR_DIRECTORYDETAIL")
public class DirDirectoryDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7232873158636678368L;

	private int id;
	private String code;
	private String code_detail;
	private String code_detail_name;
	private String memo;
	private int delete_id;
	private String create_id;
	private Date create_time;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCode_detail() {
		return code_detail;
	}
	public void setCode_detail(String code_detail) {
		this.code_detail = code_detail;
	}
	public String getCode_detail_name() {
		return code_detail_name;
	}
	public void setCode_detail_name(String code_detail_name) {
		this.code_detail_name = code_detail_name;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public int getDelete_id() {
		return delete_id;
	}
	public void setDelete_id(int delete_id) {
		this.delete_id = delete_id;
	}
	public String getCreate_id() {
		return create_id;
	}
	public void setCreate_id(String create_id) {
		this.create_id = create_id;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	

	
}
