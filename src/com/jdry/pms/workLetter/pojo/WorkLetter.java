package com.jdry.pms.workLetter.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

@Entity
@Table(name = "t_work_letter")
public class WorkLetter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer letId;
	private String docName;
	private String deptId;
	private String operId;
	private String docUrls;
	private Date insertDate;
	private Date updateDate;
	private String remark;

	private String operName;
	private String deptName;

	@Id
	@Column(name = "let_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getLetId() {
		return letId;
	}

	public void setLetId(Integer letId) {
		this.letId = letId;
	}

	@Column(name = "doc_name")
	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	@Column(name = "dept_id")
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "oper_id")
	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}

	@Column(name = "doc_urls")
	public String getDocUrls() {
		return docUrls;
	}

	public void setDocUrls(String docUrls) {
		this.docUrls = docUrls;
	}

	@Column(name = "insert_date")
	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	@Column(name = "update_date")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Formula("(SELECT t.CNAME_ FROM bdf2_user t WHERE t.USERNAME_= oper_id)")
	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	@Formula("(SELECT t.NAME_ FROM bdf2_dept t WHERE t.ID_= dept_id)")
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
