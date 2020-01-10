package com.jdry.pms.msgpublic.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 审核信息
 * 
 * @author 钟涛
 * @version 1.0 2016-01-05
 */
@Entity
@Table(name = "t_msgpub_auinfo")
public class MsgPubAuditinfo{
	/**
	 * 主键ID
	 */
	protected String ntcAuditId;
	/**
	 * @return 主键ID
	 */
	@Id
	@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "ntc_audit_id")
	public String getNtcAuditId() {
		return ntcAuditId;
	}
	/**
	 * @param ntcAuditId 主键ID
	 */
	public void setNtcAuditId(String ntcAuditId) {
		this.ntcAuditId = ntcAuditId;
	}

	/**
	 * 审核意见
	 */
	protected String ntcAuditContnt;
	
	/**
	 * @return 审核意见
	 */
	@Column(name="ntc_audit_content")
	public String getNtcAuditContnt() {
		return ntcAuditContnt;
	}
	
	/**
	 * @param ntcAuditContnt 审核意见
	 */
	public void setNtcAuditContnt(String ntcAuditContnt) {
		this.ntcAuditContnt = ntcAuditContnt;
	}
	
	/**
	 * 审核人
	 */
	protected String ntcAuditor;
	
	/**
	 * @return 审核人
	 */
	@Column(name="ntc_audit_auditor")
	public String getNtcAuditor() {
		return ntcAuditor;
	}
	
	/**
	 * @param ntcAuditor 审核人
	 */
	public void setNtcAuditor(String ntcAuditor) {
		this.ntcAuditor = ntcAuditor;
	}
	/**
	 * 审核人Id
	 */
	protected String ntcAuditorId;
	
	/**
	 * @return 审核人Id
	 */
	@Column(name="ntc_audit_auditor_id")
	public String getNtcAuditorId() {
		return ntcAuditorId;
	}
	
	/**
	 * @param ntcAuditorId 审核人Id
	 */
	public void setNtcAuditorId(String ntcAuditorId) {
		this.ntcAuditorId = ntcAuditorId;
	}
	/**
	 * 审核时间
	 */
	@JSONField (format="yyyy-MM-dd HH:mm:ss")  
	protected Date ntcCreateTime;
	
	/**
	 * @return 审核时间
	 */
	@Column(name = "ntc_audit_create_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getNtcCreateTime() {
		return ntcCreateTime;
	}
	
	/**
	 * @param ntcCreateTime 审核时间
	 */
	public void setNtcCreateTime(Date ntcCreateTime) {
		this.ntcCreateTime = ntcCreateTime;
	}
	
	/**
	 * 审核是否通过
	 */
	protected String ntcPassFlag;
	
	/**
	 * @return 审核是否通过
	 */
	@Column(name = "ntc_audit_pass_flag")
	public String getNtcPassFlag() {
		return ntcPassFlag;
	}
	
	/**
	 * @param ntcPassFlag 审核是否通过
	 */
	public void setNtcPassFlag(String ntcPassFlag) {
		this.ntcPassFlag = ntcPassFlag;
	}
	
	/**
	 * 所属公告
	 */
	private MsgPubMain ntcNotice;
	/**
	 * @return 所属公告
	 */
	@ManyToOne
	@JoinColumn(name = "ntc_notice_id", insertable = false, updatable = false)
	public MsgPubMain getNtcNotice() {
		return ntcNotice;
	}
	
	/**
	 * @param ntcNotice 所属公告
	 */
	public void setNtcNotice(MsgPubMain ntcNotice) {
		this.ntcNotice = ntcNotice;
	}
	
	/**
	 * 所属公告ID
	 */
	private String ntcNoticeId;
	
	/**
	 * @return 所属公告ID
	 */
	@Column(name = "ntc_notice_id")
	public String getNtcNoticeId() {
		return ntcNoticeId;
	}
	/**
	 * @param ntcNotice 所属公告ID
	 */
	public void setNtcNoticeId(String ntcNoticeId) {
		this.ntcNoticeId = ntcNoticeId;
	}
}
